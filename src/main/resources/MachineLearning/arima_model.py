# arima_model.py
from flask import Flask, request, jsonify
import pandas as pd
from statsmodels.tsa.arima.model import ARIMA
from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)

# Database configuration
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://root:7017@localhost:3306/sales_data'
db = SQLAlchemy(app)

class Sale(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    date = db.Column(db.Date, nullable=False)
    product = db.Column(db.String(50), nullable=False)
    sales_units = db.Column(db.Integer, nullable=False)
    sales_amount = db.Column(db.Float, nullable=False)

def get_sales_data():
    sales = Sale.query.with_entities(Sale.date, Sale.sales_amount).all()
    sales_df = pd.DataFrame(sales, columns=['date', 'sales_amount'])
    sales_df['date'] = pd.to_datetime(sales_df['date'])
    sales_df.set_index('date', inplace=True)
    return sales_df

def arima_forecast(start_date, steps=30):
    sales_df = get_sales_data()
    
    if sales_df.empty:
        return {"error": "No data available for forecasting"}
    
    try:
        sales_df = sales_df.asfreq('D', fill_value=0)
        model = ARIMA(sales_df['sales_amount'], order=(5, 1, 0))
        model_fit = model.fit()

        forecast = model_fit.forecast(steps=steps)
        forecast_dates = pd.date_range(start=start_date, periods=steps)

        return dict(zip(forecast_dates.strftime('%Y-%m-%d'), forecast))
    except Exception as e:
        return {"error": str(e)}

@app.route('/forecast', methods=['GET'])
def forecast_sales():
    start_date = request.args.get('start_date')
    steps = int(request.args.get('steps', 30))
    if not start_date:
        return jsonify({"error": "start_date parameter is required"}), 400
    result = arima_forecast(start_date, steps)
    return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True)

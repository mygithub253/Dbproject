from flask import Flask, request
import pickle
import numpy as np
from sklearn.preprocessing import StandardScaler
sc=StandardScaler()
app = Flask(__name__)
model=pickle.load(open('dbmodel1.pkl','rb'))

@app.route('/hello')
def hello_world():
    print(__name__)
    return 'hello yfktgrdvj'

@app.route('/')
def home_endpoint():
    return 'Hello World!'


@app.route('/one')
def hello_one():
    return 'hello one'


@app.route('/predict', methods=["POST"])
def hello_sum():
    int_features =[int(float(x)) for x in request.form.values()]
    final_features = [np.array(int_features).tolist()]
    a=[int_features]
    prediction = model.predict(a)
    output = prediction[0]
    return str(output)


@app.route('/two')
def hello_two():
    return 'hello two'


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=80)

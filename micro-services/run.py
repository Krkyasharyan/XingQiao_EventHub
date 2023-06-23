from flask import Flask, request
from micro_services import EmailService, GptService
from flask_cors import CORS, cross_origin

app = Flask(__name__)
CORS(app, resources={r"/generate_event_description": {"origins": "*"}})

# {
#     "receiver_email": "vahagn.ghazayan@gmail.com",
#     "subject": "Hello",
#     "body": "This is a test email"
# }
@app.route('/send_email', methods=['POST', 'OPTIONS'])
@cross_origin(origin='*', headers=['Content-Type','Authorization'])
def send_email_endpoint():
    data = request.get_json()  # get data from POST request

    print(data)

    receiver_email = data.get('receiver_email')
    subject = data.get('subject')
    body = data.get('body')

    email_service = EmailService('smtp.gmail.com', 587)  # replace with your SMTP server and port

    result = email_service.send_email(receiver_email, subject, body)

    if 'error' in result:
        return result, 400

    return result

    # {
    #     "title": "football watch",
    #     "date": "evening 8:00",
    #     "location": "dorm N8",
    #     "number_of_people": "1020 people",
    #     "tags": "pizza, socilaze, have fun, cheer your team, make frineds",
    #     "tone": "frindly, funny"
    # }
@app.route("/generate_event_description", methods=['POST', 'OPTIONS'])
@cross_origin(origin='*', headers=['Content-Type','Authorization'])
def generate_event_description():
    data = request.get_json()

    # tags = data.get('tags')

    gpt_service = GptService()  # replace with your OpenAI API Key

    result = gpt_service.generate_event_info(tags=data)

    if 'error' in result:
        return result, 400

    return result


if __name__ == "__main__":
    app.run(port=5000)

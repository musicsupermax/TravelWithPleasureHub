import React, {Component} from 'react';
import styles from './signup.css';
import script from './style';
import $ from 'jquery';
import axios from 'axios';

export default class SignUpForm extends React.Component{
    constructor(props) {
        super(props);
        this.state = {
            id: -1,
            username: '',
            firstName: '',
            secondName: '',
            phoneNumber: '',
            email: '',
            emailUp: '',
            password: '',
            passwordUp: '',
            role: 'ROLE_USER',
            status: false,
            isRegistered: false,
            isLoggedIn: false
        };
        this.usernameChange = this.usernameChange.bind(this);
        this.firstNameChange = this.firstNameChange.bind(this);
        this.secondNameChange = this.secondNameChange.bind(this);
        this.emailChange = this.emailChange.bind(this);
        this.emailUpChange = this.emailUpChange.bind(this);
        this.passwordChange = this.passwordChange.bind(this);
        this.passwordUpChange = this.passwordUpChange.bind(this);
        this.phoneNumberChange = this.phoneNumberChange.bind(this);

        this.usernameBody = this.usernameBody.bind(this);
        this.firstNameBody = this.firstNameBody.bind(this);
        this.secondNameBody = this.secondNameBody.bind(this);
        this.emailBody = this.emailBody.bind(this);
        this.emailUpBody = this.emailUpBody.bind(this);
        this.passwordBody = this.passwordBody.bind(this);
        this.passwordUpBody = this.passwordUpBody.bind(this);
        this.phoneNumberBody = this.phoneNumberBody.bind(this);


        this.profileShow = this.profileShow.bind(this);
        this.passwordChangeForm = this.passwordChangeForm.bind(this);
        this.sendSignUpRequest = this.sendSignUpRequest.bind(this);
        this.sendLoginRequest = this.sendLoginRequest.bind(this);
        this.handleChoice = this.handleChoice.bind(this);
        this.isFormValid = this.isFormValid.bind(this);
        this.isEmailUnique = this.isEmailUnique.bind(this);
    }
    render(){
        return<div className="container">
            <div className="row">
                <div className="col-md-6 col-md-offset-3">
                    <div className="panel panel-login">
                        <div className="panel-heading">
                            <div className="row">
                                <div className="col-xs-6">
                                    <a href="#" className="active" id="login-form-link">Login</a>
                                </div>
                                <div className="col-xs-6">
                                    <a href="#" id="register-form-link">Register</a>
                                </div>
                            </div>
                            <hr className="html-editor-hr"/>
                        </div>
                        <div className="panel-body">
                            <div className="row">
                                <div className="col-lg-12">
                                    <form id="login-form" action="http://localhost:8080/j_spring_security_check" method="post" role="form" style={{display: 'block'}}
                                          onSubmit={(e) => {e.preventDefault();this.sendLoginRequest();}}>
                                        {this.emailBody()}
                                        {this.passwordBody()}
                                        <div className="form-group">
                                            <div className="row">
                                                <div className="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="login-submit" id="login-submit" tabIndex="4" className="form-control btn btn-login" value="Log In" />
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <form id="register-form" role="form" style={{display: 'none'}}>
                                        {this.usernameBody()}
                                        {this.firstNameBody()}
                                        {this.secondNameBody()}
                                        {this.emailUpBody()}
                                        {this.phoneNumberBody()}
                                        {this.passwordUpBody()}
                                        <div className="form-group">
                                            <div className="row">
                                                <div className="col-sm-6 col-sm-offset-3">
                                                    <input type="submit" name="register-submit" id="register-submit" tabIndex="4" className="form-control btn btn-register" value="Register Now" onClick={this.sendSignUpRequest}/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            }
    usernameChange(event) {
        this.setState({username: event.target.value})
    }

    firstNameChange(event) {
        this.setState({firstName: event.target.value})
    }

    secondNameChange(event) {
        this.setState({secondName: event.target.value})
    }

    phoneNumberChange(event) {
        this.setState({phoneNumber: event.target.value})
    }

    emailChange(event) {
        this.setState({email: event.target.value})
    }
    emailUpChange(event){
        this.setState({emailUp: event.target.value})
    }

    passwordChange(event) {
        this.setState({password: event.target.value})
    }

    passwordUpChange(event){
        this.setState({passwordUp: event.target.value})
    }

    usernameBody() {
        return<div className="form-group">
                <input type="text" name="username" id="username" tabIndex="1" className="form-control" placeholder="Username" value={this.state.username} onChange={this.usernameChange}/>
            <div id="username-error"></div>
            </div>
    }

    firstNameBody() {
        return <div className="form-group">
            <input type="text" name="fName" id="fName" tabIndex="2" className="form-control" placeholder="First name" value={this.state.firstName} onChange={this.firstNameChange}/>
            <div id="fName-error"></div>
        </div>
    }

    secondNameBody() {
        return<div className="form-group">
            <input type="text" name="username" id="username" tabIndex="3" className="form-control" placeholder="Second name" value={this.state.secondName} onChange={this.secondNameChange}/>
            <div id="sName-error"></div>
        </div>
    }

    phoneNumberBody() {
        return <div className="form-group">
            <input type="text" name="phoneNumber" id="phoneNumber" tabIndex="4" className="form-control" placeholder="380-97-444-5555" value={this.state.phoneNumber} onChange={this.phoneNumberChange}/>
            <div id="sName-error"></div>
        </div>
    }

    emailBody() {
        return<div className="form-group">
                <input type="text" name="j_username" id="username" tabIndex="1" className="form-control" placeholder="E-mail" value={this.state.email} onChange={this.emailChange}/>
                <div id="email-error"></div>
            </div>

    }
    emailUpBody(){
        return <div className="form-group">
            <input type="text" name="j_username" id="username" tabIndex="5" className="form-control" placeholder="E-mail" value={this.state.emailUp} onChange={this.emailUpChange}/>
            <div id="email-error"></div>
        </div>
    }


    passwordBody() {
        return<div className="form-group">
                <input type="password" name="j_password" id="password" tabIndex="2" className="form-control" placeholder="Password" value={this.state.password} onChange={this.passwordChange}/>
            <div id="password-error"></div>
            </div>
    }

    passwordUpBody(){
        return <div className="form-group">
            <input type="password" name="j_password" id="password" tabIndex="6" className="form-control" placeholder="Password" value={this.state.passwordUp} onChange={this.passwordUpChange}/>
            <div id="password-error"></div>
        </div>
    }

    sendSignUpRequest(e) {
        e.preventDefault();
        let value = this.state;
        let formData = new FormData();
        this.state.status = true;
        formData.append("username", value.username);
        formData.append("firstName", value.firstName);
        formData.append("secondName", value.secondName);
        formData.append("phoneNumber", value.phoneNumber);
        formData.append("email", value.emailUp);
        formData.append("password", value.passwordUp);
        formData.append("role", this.state.role);
        formData.append("status", this.state.status);
        let form = {
            username: value.username,
            firstName: value.firstName,
            secondName: value.secondName,
            phoneNumber: value.phoneNumber,
            email: value.emailUp,
            password: value.passwordUp,
        };

       // if (this.isFormValid(form)) {
            fetch("http://localhost:8080/registration",
                {
                    method: "POST",
                    body: formData
                }
            ).then(response => {
                if (response.status === 403) {
                    this.setState({
                        status: true,
                        isRegistered: true
                    })
                }
            })
       // }
    }

    isFormValid(form) {
        let isValid = true;
        if (form.firstName == '') {
            $("#fName-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>First name cannot be empty!</div>");
            isValid = false;
        } else {
            $("#fName-error").html("");
        }
        if (form.secondName == '') {
            $("#sName-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Second name cannot be empty!</div>");
            isValid = false;
        } else {
            $("#sName-error").html("");
        }
        if (form.username == '') {
            $("#username-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Username cannot be empty!</div>");
            isValid = false;
        } else {
            $("#username-error").html("");
        }
        let regExpEmail = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,13}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!regExpEmail.test(form.email)) {
            $("#email-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Not valid email!</div>");
            isValid = false;
        } else {
            this.isEmailUnique(form.email)
                .then(data => {
                    if (data) {
                        $("#email-error").html("");
                    } else {
                        $("#email-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>E-mail is not unique!</div>");
                        isValid = false;
                    }
                });
        }
        let regExpPhone = /(380+[0-9]{9})/;
        if (!regExpPhone.test(form.phoneNumber)) {
            $("#phoneNumber-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Phone is not valid!</div>");
            isValid = false;
        } else {
            $("#phoneNumber-error").html("");
        }
        if (form.password == '') {
            $("#password-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Password cannot be empty!</div>");
            isValid = false;
        } else {
            $("#password-error").html("");

        }
        return isValid;
    }

    sendLoginRequest() {
        let value = this.state;
        let formData = new FormData();
        formData.append("j_username", value.email);
        formData.append("j_password", value.password);
        axios.post("http://localhost:8080/login",
            formData, {
                headers: {
                    'Access-Control-Allow-Credentials': 'include'
                }
            }
        ).then(response => {
            if (response.status === 200){
                window.location.replace("/profile");
            }

        })
    }

    passwordChangeForm() {

    }

    profileShow() {

    }

    handleChoice(e) {
        e.preventDefault();
        this.setState(
            {isRegistered: true}
        )
    }

    isEmailUnique(email) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: "http://localhost:8080/loginCheck",
                type: 'GET',
                data: {email: email},
                success(data) {
                    resolve(data);
                }
            });
        });
    }

}


import React, {Component} from 'react';
import axios from 'axios';
import $ from 'jquery';

export default class Profile extends Component{
    constructor(props){
        super(props);

        this.state =
            {
                user: {
                    id: -1,
                    username: "",
                    firstName: "",
                    secondName: "",
                    email: "",
                    phoneNumber: "",
                },
                changed: false,
                isLoggedOut: false
            };

        this.usernameChange = this.usernameChange.bind(this);
        this.firstNameChange = this.firstNameChange.bind(this);
        this.secondNameChange= this.secondNameChange.bind(this);
        this.phoneNumberChange= this.phoneNumberChange.bind(this);

        this.usernameBody = this.usernameBody.bind(this);
        this.firstNameBody = this.firstNameBody.bind(this);
        this.secondNameBody = this.secondNameBody.bind(this);
        this.phoneNumberBody = this.phoneNumberBody.bind(this);

        this.handleLogout = this.handleLogout.bind(this);
        this.handleSubmitChange = this.handleSubmitChange.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.isProfileValid = this.isProfileValid.bind(this);
        this.isPasswordFormValid = this.isPasswordFormValid.bind(this);
    }


    componentWillMount() {
        axios.get(`http://localhost:8080/profile`,
            {
                headers: {
                    'Access-Control-Allow-Credentials': 'include'
                }
            })
            .then(response => {
                this.setState({
                    user: response.data
                })
            });
    }

    render(){
        let value = this.state;
        return(<div className="container">
                <div className="row">
                    <div className="col-md-12  toppad  offset-md-0 ">
                        <button className="btn btn-danger float-right"  onClick={this.handleLogout}>LOG OUT</button>
                    </div>
                    <div className="col-md-6  offset-md-0  toppad" >
                        <div className="card">
                            <div className="card-body">
                                <h2 className="card-title">{this.state.user.email}</h2>
                                <table className="table table-user-information">
                                    <tbody>
                                    <tr>
                                        <td>Username:</td>
                                        <td>
                                            {this.usernameBody()}
                                        </td>
                                    </tr>
                                    {this.firstNameBody()}
                                    {this.secondNameBody()}
                                    {this.phoneNumberBody()}
                                    </tbody>
                                </table>
                                <a href="#" onClick={this.handleSubmitChange} className="btn btn-primary ml-2">Update profile</a>

                            </div>
                        </div>
                    </div>
                    <div className="col-md-6  offset-md-0  toppad">
                        <div className="card">
                            <div className="card-body">
                                <h3 className="card-title">Change password</h3>
                                <table className="table table-user-information ">
                                    <tbody>
                                    <tr>
                                        <td>New password:</td>
                                        <td>
                                            <input
                                                icon="password-icon"
                                                id="newPassword"
                                                name="newPassword"
                                                type="password"
                                                defaultValue=''
                                            />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Repeat password:</td>
                                        <td>
                                            <input
                                                icon="password-icon"
                                                id="repeatPassword"
                                                name="repeatPassword"
                                                type="password"
                                                defaultValue=''
                                            />
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <a href="#" onClick={this.handleChangePassword} className="btn btn-primary ml-2">Submit change</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    isProfileValid(form){
        let isValid = true;
        if (form.firstName === '') {
            $("#fName-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>First name cannot be empty!</div>");
            isValid = false;
        } else {
            $("#fName-error").html("");
        }
        if (form.secondName === '') {
            $("#sName-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Second name cannot be empty!</div>");
            isValid = false;
        } else {
            $("#sName-error").html("");
        }
        if (form.username === '') {
            $("#username-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Username cannot be empty!</div>");
            isValid = false;
        } else {
            $("#username-error").html("");
        }
        let regExpPhone = /(380+[0-9]{9})/;
        if (!regExpPhone.test(form.phoneNumber)) {
            $("#phoneNumber-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Phone is not valid!</div>");
            isValid = false;
        } else {
            $("#phoneNumber-error").html("");
        }
        if (form.password === '') {
            $("#password-error").fadeOut(3000).html("<br/><div class='alert alert-danger'>Password cannot be empty!</div>");
            isValid = false;
        } else {
            $("#password-error").html("");

        }
        return isValid;
    }

    handleLogout(e){
        e.preventDefault();
        axios.get("http://localhost:8080/logout").then(
            response => {
                if (response.status === 200){
                    window.location.replace("/");
                    this.setState({
                        email: ""
                    })
                }
            }
        )
    }

    handleSubmitChange(e){
        e.preventDefault();
        let user = this.state.user;
        let changeUser =new FormData();
        changeUser.append("id",user.id);
        changeUser.append("username",$("#username").val());
        changeUser.append("firstName",$("#firstName").val());
        changeUser.append("secondName",$("#secondName").val());
        changeUser.append("email",user.email);
        changeUser.append("phoneNumber",$("#phoneNumber").val());
        changeUser.append("password",user.password);
        changeUser.append("status","true");
        changeUser.append("role","ROLE_USER");
        let form = {
            username: $("#username").val(),
            firstName: $("#firstName").val(),
            secondName:$("#secondName").val(),
            phoneNumber:$("#phoneNumber").val(),
        };
        if (this.isProfileValid(form)){
            fetch("http://localhost:8080/change",
                {
                    method: "PUT",
                    body: changeUser
                })
        }
    }
    isPasswordFormValid( newPassword, newPasswordCopy) {
        var isValid = true;
        if (newPassword === '') {
            $("#newPassword-error").html("<br/><div class='alert alert-danger'>New password cannot be empty!</div>");
            isValid = false;
        } else if (newPassword !== newPasswordCopy) {
            $("#newPassword-error").html("");
            $("#newPasswordCopy-error").html("<br/><div class='alert alert-danger'>Passwords aren't same!</div>");
            isValid = false;
        } else {
            $("#newPassword-error").html("");
        }
        return isValid;
    }
    handleChangePassword(e){
        e.preventDefault();
        let user = this.state.user;
        let changeUser =new FormData();
        changeUser.append("id",user.id);
        changeUser.append("username",user.username);
        changeUser.append("firstName",user.firstName);
        changeUser.append("secondName",user.secondName);
        changeUser.append("email",user.email);
        changeUser.append("phoneNumber",user.phoneNumber);
        changeUser.append("password",$("#newPassword").val());
        changeUser.append("status","true");
        changeUser.append("role","ROLE_USER");


        let newPassword = $("#newPassword").val();
        let repeatPassword = $("#repeatPassword").val();
        if (this.isPasswordFormValid(newPassword,repeatPassword)){
            fetch("http://localhost:8080/change",
                {
                    method: "PUT",
                    body: changeUser
                })
        }
    }

    usernameBody(){
        return <input id="username"
                      name="zipCode"
                      type="text"
                      defaultValue={this.state.user.username}
                      onChange={this.usernameChange}
        />
    }
    firstNameBody(){
        return <tr>
            <td>First name:</td>
            <td>
                <input id="firstName"
                       type="text"
                       defaultValue={this.state.user.firstName}
                       onChange={this.firstNameChange}
                />
            </td>
        </tr>

    }

    secondNameBody(){
        return<tr>
            <td>Second name:</td>
            <td >
                <input id="secondName"
                       name="zipCode"
                       type="text"
                       defaultValue={this.state.user.secondName}
                       onChange={this.secondNameChange}
                />
            </td>
        </tr>
    }

    phoneNumberBody(){
        return <tr>

            <td>Phone</td>
            <td>
                <input id="phoneNumber"
                       name="phoneNumber"
                       type="email"
                       label=""
                       defaultValue={this.state.user.phoneNumber}
                       onChange={this.phoneNumberChange}
                />
            </td>
        </tr>
    }


    usernameChange(event){
        this.setState({username: event.target.value})
    }
    firstNameChange(event){
        this.setState({firstName: event.target.value})
    }

    secondNameChange(event){
        this.setState({secondName: event.target.value})
    }

    phoneNumberChange(event){
        this.setState({phoneNumber: event.target.value})
    }
}

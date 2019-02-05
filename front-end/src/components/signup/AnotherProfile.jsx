import React, {Component} from 'react';
import axios from 'axios';
import profile from './css/anotherProfile.css'
import profileCss from './css/profile.css'
export default class AnotherProfile extends Component{
    constructor(props){
        super(props);

        this.state =
            {
                user: {
                    username: "",
                    firstName: "",
                    secondName: "",
                    email: "",
                    phoneNumber: ""
                },
                changed: false,
                isLoggedOut: false
            };
    }


    componentDidMount() {
        axios.get(`http://localhost:8080/profile/${this.props.match.params.id}`,
            {
                headers: {
                    'Access-Control-Allow-Credentials': 'include'
                },
                params:{
                    "email": this.props.email
                }
            })
            .then(json => this.setState({user: json.data, changed: true, status: json.status}));
    }

    render(){
        const value = this.state.user;
        return(<div className="back1">
                <figure className="snip0051">
                    <img src="http://www.pano1544.com/photo/yt3.ggpht.com/-Ll1NN8njjrQ/AAAAAAAAAAI/AAAAAAAAAAA/C8QrE1kAx4Y/s900-c-k-no-mo-rj-c0xffffff/photo.jpg" alt="sample1"/>
                    <div className="icons">
                        <a href="http://localhost:3000/welcome"><i className="ion-ios-home-outline"></i></a>
                    </div>
                    <figcaption>
                        <h2>{value.firstName} <span>{value.secondName}</span></h2>
                        <p>Username: {value.username}<br/>E-mail: {value.email}<br/>Phone: {value.phoneNumber}</p>
                    </figcaption>
                </figure>
            </div>
        )
    }

}


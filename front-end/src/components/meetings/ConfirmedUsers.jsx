import React, {Component} from "react";
import axios from "axios";
import MeetingNavbar from "./MeetingNavbar";
import "./css/style.css"
import {NavLink} from "react-router-dom"
import JwPagination from 'jw-react-pagination';
import pStyle from './css/pagination.css';


export default class ConfirmedUsers extends Component {
    constructor(props) {
        super(props);
        this.state =
            {
                users: [{
                    id: -1,
                    username: "",
                    email: "",
                    firstName: "",
                    secondName: "",
                    additionalInfo: "",
                    phoneNumber: ""
                }],

                pageOfItems: [],
                isDownloaded: false,
                isConfirmed: false
            };
        this.onChangePage = this.onChangePage.bind(this);
    }

    render() {
        let value = this.state;
        return <div className="background">
            <MeetingNavbar/>
            {value.isDownloaded &&
            <div className="container meetingForm">
                <div
                    className="alert alert-light bg-light row h-100 justify-content-center align-items-center"> You're
                    watching users that are confirmed
                </div>
                {value.pageOfItems.map(item =>
                    <div key={item.id}>
                        <NavLink className="nav-link" to={`/profile/${item.id}`} key={item.id}>
                            <li className="list-group-item list-group-item-action flex-column align-items-start">
                                {item.firstName}{" " + item.secondName}
                            </li>
                        </NavLink>
                    </div>)}

                <div className="form-row text-center">
                    <div className="col-12">
                        <JwPagination
                            items={value.users}
                            onChangePage={this.onChangePage}
                            pageSize={6}
                            styles={pStyle}/>
                    </div>
                </div>
            </div>
            }
        </div>
    }

    componentWillMount() {
        axios.get(`http://localhost:8080/api/meetings/confirmed-users/${this.props.match.params.id}`)
            .then(json => (this.setState({users: json.data, isDownloaded: true})));
    }

    onChangePage(pageOfItems) {
        this.setState({pageOfItems});
    }

}
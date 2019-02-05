import React, {Component} from "react";
import axios from "axios";
import MeetingNavbar from "./MeetingNavbar";
import "./css/style.css"
import JwPagination from 'jw-react-pagination';
import pStyle from './css/pagination.css';
import {NavLink} from "react-router-dom";

export default class CreatedMeetings extends Component {
    constructor(props) {
        super(props);
        this.state =
            {
                meetings: [
                    {
                        id: -1,
                        header: "",
                        location: "",
                        timeOfAction: ""
                    }
                ],

                currentUser: {
                    id: -1

                },

                pageOfItems: []
            };
        this.onChangePage = this.onChangePage.bind(this);
    }


    render() {
        const value = this.state;
        return <div className="background">
            <MeetingNavbar/>
            <div className="container meetingForm">
                <div
                    className="alert alert-light bg-light row h-100 justify-content-center align-items-center"> You're
                    watching meetings where you're organizer
                </div>

                {value.meetings.length > 0 && value.meetings[0].id !== -1 && value.pageOfItems.map(item =>
                    <div key={item.id}>
                        <NavLink className="nav-link" to={`/meetings/show-meeting/${item.id}`}
                                 key={item.id}>
                            <li className="list-group-item list-group-item-action flex-column align-items-start">
                                {item.header.charAt(0).toLocaleUpperCase() + item.header.slice(1)}.
                                Address: {item.location}.
                                Date: {item.timeOfAction.substring(0, 22).replace("T", " ").replace("+", " (+") + ")"}
                            </li>
                        </NavLink>
                    </div>)}
                {value.meetings.length > 0 && value.meetings[0].id !== -1 && <div className="form-row text-center">
                    <div className="col-12">
                        <JwPagination
                            items={value.meetings}
                            onChangePage={this.onChangePage}
                            pageSize={6}
                            styles={pStyle}/>
                    </div>
                </div>}
            </div>
        </div>
    }

    componentWillMount() {
        axios.get(`http://localhost:8080/profile`)
            .then(json => (this.setState({currentUser: json.data})))

            .then(() => axios.get(`http://localhost:8080/api/meetings`,
                {
                    params: {
                        owner: this.state.currentUser.id
                    }
                })
                .then(json => this.setState({meetings: json.data})));
    }


    onChangePage(pageOfItems) {
        this.setState({pageOfItems});
    }

}
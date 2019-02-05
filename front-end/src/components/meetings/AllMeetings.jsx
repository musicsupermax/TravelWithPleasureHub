import React, {Component} from "react";
import MeetingNavbar from "./MeetingNavbar";
import "./css/style.css"
import {NavLink} from "react-router-dom";
import JwPagination from 'jw-react-pagination';
import pStyle from './css/pagination.css';


export default class AllMeetings extends Component {
    constructor(props) {
        super(props);
        this.state =
            {
                meetings: [
                    {
                        id: -1,
                        header: "",
                        location: "",
                        timeOfAction: "",
                    }
                ],
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
                    className="container alert alert-light bg-light row h-100 justify-content-center align-items-center"> You're
                    watching all meetings
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
                <div className="form-row text-center">
                    <div className="col-12">
                        <JwPagination
                            items={value.meetings}
                            onChangePage={this.onChangePage}
                            pageSize={6}
                            styles={pStyle}/>
                    </div>
                </div>
            </div>
        </div>
    }


    componentWillMount() {
        fetch(`http://localhost:8080/api/meetings`)
            .then(data => data.json())
            .then(json => this.setState({meetings: json}));
    }

    onChangePage(pageOfItems) {
        this.setState({pageOfItems});
    }
}
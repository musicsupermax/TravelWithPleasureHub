import React, {Component} from "react";
import axios from "axios";
import MeetingNavbar from "./MeetingNavbar";
import "./css/style.css"
import JwPagination from 'jw-react-pagination';
import pStyle from './css/pagination.css';
import {NavLink} from "react-router-dom";


export default class MeetingsHistory extends Component {
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
          
                confirmed: false,
                wishing: false,
                pageOfItems: []
            };
        this.confirmed = this.confirmed.bind(this);
        this.wishing = this.wishing.bind(this);
        this.onChangePage = this.onChangePage.bind(this);

    }

    componentWillMount() {
        axios.get("http://localhost:8080/profile"
        ).then(response => {
                this.setState({
                    currentUser: response.data
                });
            }
        )
    }

    render() {
        const value = this.state;
        return <div className="background">
            <MeetingNavbar/>
            <div className="container meetingForm">
                <div className="form-row text-center">
                    <div className="col-12">
                        <div className="btn-group btn-group-toggle" data-toggle="buttons">
                            <button type="submit"
                                    className="btn btn-secondary"
                                    onClick={this.wishing}>
                                Wishing meetings
                            </button>
                            <button type="submit"
                                    className="btn btn-secondary"
                                    onClick={this.confirmed}>
                                Confirmed meetings
                            </button>
                        </div>
                    </div>
                </div>
                {(value.confirmed || value.wishing) && <div
                    className="alert alert-light bg-light row h-100 justify-content-center align-items-center"> You're
                    watching meetings where you are {value.confirmed ? " confirmed" : "wishing want to be"} participant
                </div>}
                {value.meetings.length > 0 && value.meetings[0].id !== -1 &&
                value.pageOfItems.map(item =>
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


    confirmed(e) {
        e.preventDefault();
        axios.get(`http://localhost:8080/api/meetings`,
            {
                params: {
                    confirmedHistoryByUser: this.state.currentUser.id
                }
            })
            .then(json => this.setState({meetings: json.data, confirmed: true, wishing: false}));
    }

    wishing(e) {
        e.preventDefault();
        axios.get(`http://localhost:8080/api/meetings`,
            {
                params: {
                    wishingHistoryByUser: this.state.currentUser.id
                }
            })
            .then(json => this.setState({meetings: json.data, confirmed: false, wishing: true}));
    }

    onChangePage(pageOfItems) {
        this.setState({pageOfItems});
    }

}
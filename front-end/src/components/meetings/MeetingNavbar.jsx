import React, {Component} from "react";
import {NavLink} from "react-router-dom";
import axios from "axios/index";

export default class MeetingNavbar extends Component {

    constructor(props) {
        super(props);

        this.state =
            {
                currentUser: {
                    id: -1
                }
            };
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
        return (
            <div className="container">
                <div className="row justify-content-center">
                    <nav className="navbar navbar-expand-lg navbar-light">
                        <button className="navbar-toggler" type="button" data-toggle="collapse"
                                data-target="#navbarNavDropdown"
                                aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                            <span className="navbar-toggler-icon"/>
                        </button>
                        <div className="collapse navbar-collapse" id="navbarNavDropdown">
                            <ul className="navbar-nav">
                                <li className="nav-item active">
                                    <NavLink className="nav-link" to="/meetings/show-all-meetings">
                                        <button className="btn btn-dark">
                                            View all meetings
                                        </button>

                                    </NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/meetings/find-meetings">
                                        <button className="btn btn-dark">
                                            Find meetings
                                        </button>
                                    </NavLink>
                                </li>
                                {this.state.currentUser !== null && this.state.currentUser.id !== -1 &&
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/meetings/show-history">
                                        <button className="btn btn-dark">
                                            View history
                                        </button>
                                    </NavLink>
                                </li>}
                                {this.state.currentUser !== null && this.state.currentUser.id !== -1 &&
                                <li className="nav-item dropdown">
                                    <NavLink className="nav-link" to="/meetings/show-all-created-meetings">
                                        <button className="btn btn-dark">
                                            Your meetings
                                        </button>
                                    </NavLink>
                                </li>}
                                {this.state.currentUser !== null && this.state.currentUser.id !== -1 &&
                                <li className="nav-item dropdown">
                                    <NavLink className="nav-link" to="/meetings/add-meeting">
                                        <button className="btn btn-primary">
                                            Create meeting
                                        </button>
                                    </NavLink>
                                </li>}
                            </ul>
                        </div>
                    </nav>
                </div>
            </div>
        )

    }
}
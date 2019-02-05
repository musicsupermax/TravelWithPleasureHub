import React, {Component} from 'react';
import {NavLink} from "react-router-dom";

class NavBar extends Component {

    render(){
        return (
            <div>
                <nav className="navbar fixed-top navbar-expand-md navbar-dark bg-dark">
                    <div className="container">
                        <NavLink className="navbar-brand" to="/welcome">Travel With Pleasure</NavLink>
                        <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#menu">
                            <span className="navbar-toggler-icon"></span>
                        </button>

                        <div className="collapse navbar-collapse" id="menu">
                            <ul className="navbar-nav mr-auto">
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/tickets">Purchase a plane ticket</NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/properties">Rent a property</NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/meetings/show-all-meetings">Arrange a
                                        meeting</NavLink>
                                </li>
                                <li>
                                    <NavLink className="nav-link" to="/profile">Profile</NavLink>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </div >
        );
    }
}

export default NavBar;
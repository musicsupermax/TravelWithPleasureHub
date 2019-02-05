import React, {Component, Fragment} from 'react';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import NavBar from "./components/NavBar";
import PropertyList from "./components/property-rent/PropertyList";
import Property from "./components/property-rent/Property";
import PropertyUpload from "./components/property-rent/PropertyUpload";
import axios from 'axios';
import AllMeetings from "./components/meetings/AllMeetings";
import MeetingAdd from './components/meetings/MeetingAdd'
import CreatedMeetings from './components/meetings/CreatedMeetings'
import OneMeeting from './components/meetings/OneMeeting'
import MeetingsFind from "./components/meetings/MeetingsFind";
import MeetingsHistory from "./components/meetings/MeetingsHistory";
import ErrorBoundary from "./ErrorBoundary";
import NoMatch from "./components/NoMatch";
import Home from "./components/Home";
import TicketsForm from "./components/tickets/TicketsForm";
import Profile from './components/signup/Profile';
import WishingUsers from "./components/meetings/WishingUsers";
import ConfirmedUsers from "./components/meetings/ConfirmedUsers";
import MeetingUpdate from "./components/meetings/MeetingUpdate";
import AnotherProfile from "./components/signup/AnotherProfile";
import SignUp from "./components/SignUp";
import NotFound from "./components/NotFound";
import RentedProperties from "./components/property-rent/RentedProperties";

class App extends Component {
    constructor(props){
        super(props);
        this.state = {
            user:{
                status: ''
            }
        };
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/profile`,
            {
                headers: {
                    'Access-Control-Allow-Credentials': 'include'
                }
            })
            .then(json => this.setState({user: json.data, changed: true, status: json.status}));
    }
    render() {
        return (
			<div>

                {!this.state.user.status?
					<BrowserRouter>
						<Fragment>
							<ErrorBoundary>
								<Route exact path="/" component={SignUp}/>
							</ErrorBoundary>
						</Fragment>
					</BrowserRouter>:<BrowserRouter>
						<Fragment>
							<NavBar/>
							<ErrorBoundary>
								<Switch>
									<Route exact path="/upload/property" component={PropertyUpload} />
									<Route exact path="/properties/:id" component={Property} />
									<Route exact path="/properties/" component={PropertyList} />
									<Route exact path="/rented-properties" component={RentedProperties} />

									<Route exact path="/meetings/show-all-meetings/" component={AllMeetings}/>
									<Route exact path="/meetings/find-meetings/" component={MeetingsFind}/>
									<Route exact path="/meetings/add-meeting/" component={MeetingAdd}/>
									<Route exact path="/meetings/show-history/" component={MeetingsHistory}/>
									<Route exact path="/meetings/show-all-created-meetings" component={CreatedMeetings}/>
									<Route exact path="/meetings/show-meeting/:id"  component={OneMeeting}/>
									<Route exact path="/meetings/edit/:id" component={MeetingUpdate}/>
									<Route exact path="/profile"  component={Profile}/>
									<Route exact path="/profile/:id" component={AnotherProfile}/>
									<Route path="/tickets" component={TicketsForm}/>
									<Route exact path="/meetings/show-meeting/wishing-users/:id" component={WishingUsers}/>
									<Route exact path="/meetings/show-meeting/confirmed-users/:id"
										   component={ConfirmedUsers}/>
									<Route  path="/welcome" component={Home}/>


                                    <Route path="/404" component={NotFound} />
									<Route path="*" component={NoMatch}/>
								</Switch>

							</ErrorBoundary>
						</Fragment>

					</BrowserRouter>}
			</div>
        );
    }

}

export default App;
import React, {Component} from 'react';
import ticketPic from '../pics/tickets.jpg';
import rentPic from '../pics/rent.jpg';
import meetingsPic from '../pics/meetings.jpg';
import {Link} from "react-router-dom";


class Home extends Component {
	render() {
		return (
			<div className="container">
				<div className="row">
					<div className="col-md-4">
						<Link to="/tickets" className="card">
							<img className="card-img-top" src={ticketPic} alt="Card image cap"/>
							<div className="card-body">
								<h5 className="card-title">Ticket search</h5>
							</div>
						</Link>
					</div>
					<div className="col-md-4">
						<Link className="card" to="/properties">
							<img className="card-img-top" src={rentPic} alt="Card image cap"/>
							<div className="card-body">
								<h5 className="card-title">Rental property</h5>
							</div>
						</Link>
					</div>
					<div className="col-md-4">
						<Link to="/meetings/show-all-meetings" className="card">
							<img className="card-img-top" src={meetingsPic} alt="Card image cap"/>
							<div className="card-body">
								<h5 className="card-title">Arrange a meeting</h5>
							</div>
						</Link>
					</div>
				</div>
			</div>
		);
	}
}

export default Home;
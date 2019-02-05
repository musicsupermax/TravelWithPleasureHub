import React, {Component} from 'react';
import PropertyRentNavbar from "./PropertyRentNavbar";
import {Link} from "react-router-dom";

class RentedProperties extends Component {
	constructor(props) {
		super(props);
		this.state = {
			currentUserId: null,
			propertyBookDatesOfUser: []
		};

		this.loadCurrentUser = this.loadCurrentUser.bind(this);
		this.loadPropertyBookDatesOfUser = this.loadPropertyBookDatesOfUser.bind(this);
	}

	unbookPeriod = (id) => {
		fetch(`http://localhost:8080/api/property-availability/${id}`,{
			method: 'DELETE'
		})
			.then(() => this.loadPropertyBookDatesOfUser())
			.catch(() => { throw 'Error during unbooking!' } )
	};

	loadCurrentUser = () => {
		fetch('http://localhost:8080/profile')
			.then(response => response.json())
			.then(response => {
				console.log(response);
				this.setState({currentUserId: response.id});
			})
			.then(() => this.loadPropertyBookDatesOfUser())
			.catch(() => { throw 'Please, authorize'} )
	};

	loadPropertyBookDatesOfUser = () => {
		fetch(`http://localhost:8080/api/property-availability/user/${this.state.currentUserId}`)
			.then(response => response.json())
			.then(response => {
				console.log(response);
				this.setState({propertyBookDatesOfUser : response})
			})
			.catch(error => { throw error })
	};

	componentWillMount() {
		this.loadCurrentUser();
	}


	render() {
		return (
			<div className="container">
				<PropertyRentNavbar/>
				{
					this.state.currentUserId !== null ?
						<div>
							<h2 className="w-100 text-center">Properties you have rented</h2>
							<div className="d-flex justify-content-center">
								<div className="col-5 text-black text-center">
									{
										this.state.currentUserId !== null &&
										this.state.propertyBookDatesOfUser.map((period, index) =>
											<div key={index}>
												<div className="row alert alert-secondary">
													<Link className="col-8 text-black" to={`/properties/${period.property.id}`}>
														<div>
															You have booked property in {period.property.locality} on {period.property.address} since {period.bookedSince} until {period.bookedUntil}
														</div>
													</Link>
													<div className="col-2">
														<button className="btn btn-danger btn-lg"
														        onClick={() => this.unbookPeriod(period.id)}>Unbook</button>
													</div>
												</div>
											</div>
										)
									}
								</div>
							</div>
						</div>
						:
						<h2>Please, authorize</h2>
				}

			</div>
		);
	}
}

export default RentedProperties;
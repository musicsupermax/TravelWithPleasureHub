import React, {Component} from 'react';
import Calendar from 'react-calendar';
import moment from 'moment';


class Property extends Component {
	constructor(props) {
		super(props);
		this.state = {
			id: null,
			title: "",
			description: "",
			locality: "",
			address: "",
			price: "",
			checkIn: "",
			checkOut: "",
			imageLinkObjects: [],
			propertyBookedDates: [],
			successfullyBooked: false,
			messageType: "",
			message: "",
			currentUserId: null,
			ownerId: null,
			propertyBookDatesOfUser: []
		};

		this.loadPropertyData = this.loadPropertyData.bind(this);
		this.loadImages = this.loadImages.bind(this);
		this.onCalendarDateChange = this.onCalendarDateChange.bind(this);
		this.loadPropertyAvailability = this.loadPropertyAvailability.bind(this);
		this.onCalendarDateChange = this.onCalendarDateChange.bind(this);
		this.loadCurrentUser = this.loadCurrentUser.bind(this);
	}


	loadPropertyData = () => {
		fetch(`http://localhost:8080/api/properties/${this.props.match.params.id}`)
			.then(response => response.json())
			.then(response => {
				if(!response.title) {
					this.props.history.push('/404');
				} else this.setState(response);

			})
			.then(() => this.loadPropertyBookDatesOfUser())
			.catch(error => { throw error } )
	};

	loadImages = () => {
		fetch(`http://localhost:8080/api/property-image/property/${this.props.match.params.id}`)
			.then(response => response.json())
			.then(responseJSON => {
				if(responseJSON[0])
					this.setState({imageLinkObjects: responseJSON});
				else
					this.setState({imageLinkObjects: Array.from(['https://i.imgur.com/SCJSLeS.png'])})
			})
			.catch(error => { throw error } )
	};

	loadPropertyAvailability = () => {
		fetch(`http://localhost:8080/api/property-availability/property/${this.props.match.params.id}`)
			.then(response => response.json())
			.then(response => {
				this.setState({
					propertyBookedDates: this.getAllBookedDates(response)
				})
			})
			.catch(error => { throw error } )
	};

	componentWillMount() {
		this.loadCurrentUser();
		this.loadPropertyAvailability();
		this.loadImages();
	}

	loadCurrentUser = () => {
		fetch('http://localhost:8080/profile')
			.then(response => response.json())
			.then(response => {
				this.setState({currentUserId: response.id});
			})
			.then(() => this.loadPropertyData())
			.catch(() => { throw 'Please, authorize'} )
	};

	loadPropertyBookDatesOfUser = () => {
		fetch(`http://localhost:8080/api/property-availability?userId=${this.state.currentUserId}&propertyId=${this.state.id}`)
			.then(response => response.json())
			.then(response => {
				this.setState({propertyBookDatesOfUser : response})
			})
			.catch(error => { throw error })
	};


	getAllDatesBetweenStartAndEndDate(start, end) {
		let dateArray = [];
		let currentDate = moment(start);
		let endDate = moment(end);
		while (currentDate <= endDate) {
			dateArray.push(moment(currentDate).format('YYYY-MM-DD') );
			currentDate = moment(currentDate).add(1, 'days');
		}
		return dateArray;
	};

	parsePropertyBookedIntoPeriods(array) {
		let arrayOfPeriods = [];
		let period = [];
		for (let i = 0; i < array.length; i++) {
			period.push([array[i].bookedSince, array[i].bookedUntil]);
		}
		arrayOfPeriods.push(period);
		return arrayOfPeriods;
	};

	getBookedDatesInPeriods(arrayOfPeriods) {
		let arrayOfBookedDates = [];
		for (let i = 0; i < arrayOfPeriods.length; i++) {
			for (let j = 0; j < arrayOfPeriods[i].length; j++) {
				arrayOfBookedDates.push(this.getAllDatesBetweenStartAndEndDate(arrayOfPeriods[i][j][0], arrayOfPeriods[i][j][1]))
			}
		}
		return arrayOfBookedDates;
	}

	getAllBookedDates(response) {
		let arrayOfBookedDatesPeriod = this.getBookedDatesInPeriods(this.parsePropertyBookedIntoPeriods(response));
		let allBookedDates = [];
		for (let i = 0; i < arrayOfBookedDatesPeriod.length; i++) {
			for (let j = 0; j < arrayOfBookedDatesPeriod[i].length; j++) {
				allBookedDates.push(arrayOfBookedDatesPeriod[i][j])
			}
		}
		return allBookedDates;
	}

	onCalendarDateChange = (date) => {
		this.setState({
			checkIn: moment(date[0]).format('YYYY-MM-DD'),
			checkOut: moment(date[1]).format('YYYY-MM-DD')
		});
	};

	onBookButtonClick = () => {
		this.setState({
			messageType: 'dark',
			message: 'Loading...'
		});
		let formData = new FormData();
		formData.append('checkIn', this.state.checkIn);
		formData.append('checkOut', this.state.checkOut);
		formData.append('propertyId', this.props.match.params.id);
		formData.append('currentUserId', this.state.currentUserId);
		fetch('http://localhost:8080/api/property-availability', {
			method: "POST",
			body: formData
		})
			.then(response => {
				if(response.ok) {
					this.setState({
						messageType: 'success',
						message: 'Success! You have successfully booked since '
							+ this.state.checkIn + ' until ' + this.state.checkOut
					});
					this.loadPropertyBookDatesOfUser();
				} else {
					this.setState({
						messageType: 'danger',
						message: 'Error'
					});
				}
				return response.json()
			})
			.then(() => this.loadPropertyAvailability())
			.catch(error => { throw error } )
	};

	unbookPeriod = (id) => {
		fetch(`http://localhost:8080/api/property-availability/${id}`,{
			method: 'DELETE'
		})
			.then(() => {
				this.setState({
					message: "",
					messageType: ""
				});
				this.loadPropertyBookDatesOfUser()
			})
			.then(() => this.loadPropertyAvailability())
	};


	render() {
		return (
			<div className="container">
				<div id="carouselExampleIndicators" className="carousel slide" data-interval="false"
				     data-ride="carousel">
					<ol className="carousel-indicators">
						{
							this.state.imageLinkObjects.map((_, index) =>
								index === 0 ?
									<li key={index} data-target="#carouselExampleIndicators" data-slide-to={index} className="active"/>
									:
									<li key={index} data-target="#carouselExampleIndicators" data-slide-to={index}/>
							)
						}
					</ol>
					<div className="carousel-inner">
						{
							this.state.imageLinkObjects.map((imageLinkObject, index) =>

								index === 0 ?
									<div key={index} className="carousel-item active">
										<img className="d-block w-100" src={imageLinkObject.imageLink}/>
									</div>
									:
									<div key={index} className="carousel-item"> :
										<img className="d-block w-100" src={imageLinkObject.imageLink}/>
									</div>

							)
						}
					</div>
					<a className="carousel-control-prev" href="#carouselExampleIndicators" role="button"
					   data-slide="prev">
						<span className="carousel-control-prev-icon" aria-hidden="true"/>
						<span className="sr-only">Previous</span>
					</a>
					<a className="carousel-control-next" href="#carouselExampleIndicators" role="button"
					   data-slide="next">
						<span className="carousel-control-next-icon" aria-hidden="true"/>
						<span className="sr-only">Next</span>
					</a>
				</div>
				<div className="row mt-3 justify-content-around">
					<div className="col-11  text-black">
						<h1>{this.state.title}</h1>
					</div>
					<p className="col-11">{this.state.locality},  {this.state.address}</p>
				</div>

				<div className="row mt-4 justify-content-around">
					<div className="col-6 text-black">
						<p>{this.state.description}</p>
					</div>
					<div className="col-5 text-black text-center">
						{
							this.state.currentUserId !== null &&
								this.state.propertyBookDatesOfUser.map((period, index) =>
									<div key={index}>
										<div className="row alert alert-secondary">
											<div className="col-8">
												You have booked this property rent since {period.bookedSince} until {period.bookedUntil}
											</div>
											<div className="col-2">
												<button className="btn btn-danger btn-lg"
												        onClick={() => this.unbookPeriod(period.id)}>Unbook</button>
											</div>
										</div>
									</div>
								)
						}
						{
							this.state.messageType !== "" &&
							<div className={"alert alert-" + this.state.messageType}>
								{this.state.message}
							</div>
						}
						<div className="d-flex justify-content-center">
							<Calendar
								onChange={this.onCalendarDateChange}
								selectRange={true}
								tileDisabled={({date, view}) =>
									(view === 'month') &&
									this.state.propertyBookedDates.some((disabledDateString) => {
											const disabledDate = new Date(disabledDateString);
											return date.getFullYear() === disabledDate.getFullYear() &&
												date.getMonth() === disabledDate.getMonth() &&
												date.getDate() === disabledDate.getDate()
										}
									)}
							/>
						</div>
						{
							this.state.currentUserId !== null &&
								<button onClick={this.onBookButtonClick} type="button"
								        className="btn btn-primary mt-2 mb-2">Book</button>
						}
					</div>
				</div>
			</div>
		);
	}
}

export default Property;
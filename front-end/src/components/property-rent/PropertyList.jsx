import React, {Component} from 'react';
import PropertyThumbnail from "./PropertyThumbnail";
import PropertyOnMap from "./map/PropertyOnMap";
import PropertyRentNavbar from "./PropertyRentNavbar";
import JwPagination from "jw-react-pagination";
import pStyle from "../meetings/css/pagination.css";

class PropertyList extends Component {
	constructor(props) {
		super(props);
		this.state = {
			properties: [],
			locality: "",
			address: "",
			checkIn: "",
			checkOut: "",
			displayMap: false,
			mapCenterLatitude: null,
			mapCenterLongitude: null,
			pageOfItems: []
		};
		this.loadProperties = this.loadProperties.bind(this);
		this.onFieldChange = this.onFieldChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.loadMatchingProperties = this.loadMatchingProperties.bind(this);
		this.loadCoordinatesOfAddress = this.loadCoordinatesOfAddress.bind(this);
		this.onChangePage = this.onChangePage.bind(this);
	}

	loadProperties = () => {
		fetch('http://localhost:8080/api/properties')
			.then(response => response.json())
			.then(properties => this.setState({properties : properties}))
			.catch(error => { throw error } )
	};

	onChangePage(pageOfItems) {
		this.setState({pageOfItems});
	}

	onFieldChange = e => {
		this.setState({ [e.target.name]: e.target.value });
	};

	handleSubmit = e => {
		e.preventDefault();
		this.loadMatchingProperties();
	};

	loadMatchingProperties = () => {
		this.setState({displayMap: false});
		let formData = new FormData();
		formData.append('locality', this.state.locality);
		formData.append('address', this.state.address);
		formData.append('checkIn', this.state.checkIn);
		formData.append('checkOut', this.state.checkOut);
		fetch('http://localhost:8080/api/properties/search', {
			method: "POST",
			body: formData
		})
			.then(response => response.json())
			.then(properties => {
				console.log(properties);
				if (properties.length === 0)
					this.loadCoordinatesOfAddress();
				this.setState({
					properties: properties,
				});

			})
			.catch(error => { throw error } )

	};

	loadCoordinatesOfAddress = () => {
		this.setState({displayMap: false});
		if(this.state.address === "" && this.state.locality === "") return;
		let fullAddress = this.state.address + ' ' + this.state.locality;
		fullAddress = fullAddress.replace(/ /g, '+');
		fetch(`https://geocoder.api.here.com/6.2/geocode.json?searchtext=${fullAddress}&app_code=${'AR8OAZmJZPtl8mNegMW94w'}&app_id=${'6J9fCy6htv1xIoObLj3n'}`)
			.then(response => response.json())
			.then(response => {
				if(response.Response.View[0]) {
					let coordinates = response.Response.View[0].Result[0].Location.NavigationPosition[0];
					this.setState({
						mapCenterLatitude: coordinates.Latitude,
						mapCenterLongitude: coordinates.Longitude,
						displayMap: true
					})
				}
			})
			.catch(error => { throw error } )
	};

	componentWillMount() {
		this.loadProperties();
	}


	render() {

		return (
			<div className="container">
				<PropertyRentNavbar />
				<h2 className="ml-3 ">Search property you need!</h2>
				<form className="mt-2 ml-3" onSubmit={this.handleSubmit}>
					<div className="form-row">
						<div className="col">
							<label htmlFor="locality">Locality</label>
							<input type="text" className="form-control" placeholder="London" id="locality"
							       name="locality" onChange={this.onFieldChange}/>
						</div>
						<div className="col">
							<label htmlFor="address">Address</label>
							<input type="text" className="form-control" placeholder="Main street" id="address"
							       name="address" onChange={this.onFieldChange}/>
						</div>
						<div className="col">
							<label htmlFor="check-in">Check in</label>
							<input type="date" className="form-control" id="check-in"
							       name="checkIn" onChange={this.onFieldChange}/>
						</div>
						<div className="col">
							<label htmlFor="check-out">Check out</label>
							<input type="date" className="form-control" id="check-out"
							       name="checkOut" min={this.state.checkIn} onChange={this.onFieldChange}/>
						</div>
						<div className="d-flex flex-column">
							<button type="submit" className="btn btn-primary mt-auto">Submit</button>
						</div>
					</div>
				</form>
				<div className="p-3">
					<div className="row justify-content-center">
						{
							this.state.properties.length !== 0 ?
								this.state.pageOfItems.map((property, index) =>
									<div key={index} className="col-md-4">
											<PropertyThumbnail id={property.id} title={property.title}
										                   price={property.price} />
									</div>
								)
								:
								<h1>Nothing was found at this address</h1>

						}

					</div>
				</div>
				{
					this.state.properties.length !== 0 && <div className="form-row text-center">
						<div className="col-12">
							<JwPagination
								items={this.state.properties}
								onChangePage={this.onChangePage}
								pageSize={6}
								styles={pStyle}/>
						</div>
					</div>
				}
				{
					this.state.displayMap === true && <PropertyOnMap
					centerLatitude={this.state.mapCenterLatitude}
						centerLongitude={this.state.mapCenterLongitude}/>
				}
			</div>

		);
	}
}

export default PropertyList;
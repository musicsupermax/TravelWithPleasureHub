import React, {Component} from 'react';
import ReactMapGL, {Marker, Popup} from 'react-map-gl';
import CityPin from "./CityPin";
import {defaultMapStyle} from './map-style.js';
import PropertyInfo from "./PropertyInfo";

export default class Map extends Component {

	constructor(props) {
		super(props);
		console.log(props);
		this.state = {
			mapStyle: defaultMapStyle,
			viewport: {
				width: "100%",
				height: 800,
				latitude: props.centerLatitude,
				longitude: props.centerLongitude,
				zoom: 14
			},
			properties: [],
			propertyInfo: null,
			imageLink: null
		};

		this.loadPropertiesWithin5km = this.loadPropertiesWithin5km.bind(this);
		this.loadThumbnailImageLink = this.loadThumbnailImageLink.bind(this);
	}

	loadPropertiesWithin5km = () => {
		fetch(`http://localhost:8080/api/properties/5km?latitude=${this.props.centerLatitude}&longitude=${this.props.centerLongitude}`)
			.then(response => response.json())
			.then(properties => {
				this.setState({properties : properties})
			})
			.catch(error => { throw error } )
	};

	componentWillMount() {
		this.loadPropertiesWithin5km();
	}

	loadThumbnailImageLink = (id) => {
		fetch('http://localhost:8080/api/property-image/property/first/' + id)
			.then(response => response.json())
			.then(responseJSON => this.setState({
				imageLink: responseJSON.imageLink
			}))
			.catch(error => { throw error } )
	};

	renderPopup() {
		if(this.state.propertyInfo !== null) {
			const {propertyInfo} = this.state;
			this.loadThumbnailImageLink(propertyInfo.id)
			return propertyInfo && (
				<Popup tipSize={5}
				       anchor="top"
				       longitude={propertyInfo.longitude}
				       latitude={propertyInfo.latitude}
				       closeOnClick={false}
				       onClose={() => this.setState({propertyInfo: null})} >
					<PropertyInfo property={propertyInfo} imageLink={this.state.imageLink}/>
				</Popup>
			);
		}
	}

	render() {
		const token = 'pk.eyJ1IjoiZXNoa2VyZTExMSIsImEiOiJjanIyM21majYwaW9sNDNycGV5YWtqdm0yIn0.poK9vlluAxaRBa27NFmNeA';
		return (
			<div>
				{
					this.state.properties.length !== 0 &&
						<div>
							<h2>Check out the properties within a 5 km radius!</h2>
							<ReactMapGL
								mapboxApiAccessToken={token}
								{...this.state.viewport}
								mapStyle={this.state.mapStyle}
								onViewportChange={(viewport) => this.setState({viewport})}>
								{
									this.state.properties.map(property =>
										<Marker key={property.id} latitude={property.latitude} longitude={property.longitude}>
											<CityPin onClick={() => this.setState({propertyInfo: property})}/>
										</Marker>
									)
								}
								{this.renderPopup()}
							</ReactMapGL>
						</div>
				}
			</div>
		);
	}
}


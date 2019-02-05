import React, {Component} from 'react';
import Map from "./Map";

export default class PropertyOnMap extends Component {
	constructor(props) {
		super(props);
	}
	render() {
		return (
			<div>
				<Map centerLatitude={this.props.centerLatitude}
				     centerLongitude={this.props.centerLongitude}/>
			</div>
		);
	}
}


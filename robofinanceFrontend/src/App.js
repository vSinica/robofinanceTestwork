import './App.css';
import React, {Component} from "react";
import AddFindEditCustomer from "./components/CustomerList/AddFindEditCustomer";

export default class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };

    }

    render(){
        return (
                <div className='app-wrapper'>
                    <AddFindEditCustomer />
                </div>
        );
    }
}






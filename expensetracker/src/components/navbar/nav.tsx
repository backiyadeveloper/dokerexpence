import "./nav.css"
import { Link } from "react-router-dom";
import navimg from "../../assets/images.png";
import {navigate} from "../../service/nav"
export function Navbar(){ 
    const {logout}=navigate();
    return(
        <>
          <div className="navborder">
            <img src={navimg} className="navimg"  alt=""></img>
            <nav className="navtag">
                <Link to={"/home"} className="atag"><a >Home</a></Link>
                <Link to={"/expense"} className="atag"><a >Expens and category</a></Link>
                <Link to={"/income"} className="atag"> <a >Income</a></Link>
                <Link to={"/user"} className="atag"><a href="./user.html">User</a></Link>
                <Link to={"/viewbycategory"} className="atag"><a>view by Category</a></Link>
                <Link to={"/Daterange"} className="atag"><a >view by dates</a></Link>
                <Link to={"/Report"} className="atag"><a href="./report.html">Report</a></Link>
                <Link to={"/logout"} className="atag" onClick={logout}> <a  >logout</a></Link>
            </nav>
          </div>
        </>
    )
}
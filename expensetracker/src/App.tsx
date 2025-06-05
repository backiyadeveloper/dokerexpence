
import {Home} from "./components/homepage/home"
import './App.css'
import {Navbar} from "./components/navbar/nav";
import { Routes,Route, BrowserRouter } from 'react-router-dom';
import Login from "./components/login/login"
import Addexpense from "./components/expense/addExpense"
import {Addincome} from "./components/income/addincome"
import {Viewbycategory} from "./components/viewbycategory/viewBycategory"
import {Daterange} from "./components/daterange/daterange"
import {Report} from "./components/report/report"
import {User} from "./components/user/user"
function App() {

  return (
    <>
    
    <BrowserRouter>
      <Routes>
        <Route path='/' element={ <Login></Login>}></Route>
        <Route path='/home' element={[<Navbar></Navbar>,<Home></Home>]}></Route>
        <Route path='/expense' element={[<Navbar></Navbar>,<Addexpense></Addexpense>]}></Route>
        <Route path='/income' element={[<Navbar></Navbar>,<Addincome></Addincome>]}></Route>
        <Route path='/viewbycategory' element={[<Navbar></Navbar>,<Viewbycategory></Viewbycategory>]}></Route>
        <Route path='/Daterange' element={[<Navbar></Navbar>,<Daterange></Daterange>]}></Route>
        <Route path='/Report' element={[<Navbar></Navbar>,<Report></Report>]}></Route>
        <Route path='/user' element={[<Navbar></Navbar>,<User></User>]}></Route>
        <Route path='/logout' element={ <Login></Login>}></Route>
      </Routes>
    </BrowserRouter>

    </>
  )
}

export default App

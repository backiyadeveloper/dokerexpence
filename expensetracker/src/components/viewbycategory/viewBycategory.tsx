import { UseViewCategory } from "../../service/viewbycategoryservice";
import "./viewbycategory.css";
export const Viewbycategory = () => {
  const { categories, handleChange, categorytable, expense } = UseViewCategory()
  return (
    <div className="card-container ">
      <div className="v1div">
        <div className="viewdiv">
          <h3>View By Category</h3>
          <form>
            <label htmlFor="categorybx">Enter a category:</label>
            <select
              name="category"
              id="categorybx"
              defaultValue=""
              onChange={(e)=> handleChange(e.target.value)}>
              <option value="" disabled>
                -- Select a Category --
              </option>
              {categories.map((category, index) => (
                <option key={index} value={category}>
                  {category}
                </option>
              ))}
            </select>
            <button className="incomexpens" onClick={categorytable}>
              Search
            </button>
          </form>
        </div>
      </div>
      {expense.length > 0 ? (
        <div className="viewdiv" id="displaytable">
          <h1>View By Category</h1>
          <center>
            <table id="expenstable">
              <thead>
                <tr>
                  <th>Amount</th>
                  <th>Description</th>
                  <th>Category</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {expense.map((item, index) => (
                  <tr key={index}>
                    <td>{item.amount}</td>
                    <td>{item.description}</td>
                    <td>{item.category}</td>
                    <td>{item.date}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </center>
        </div>
      ) : (
        <></>
      )}
    </div>
  );
};

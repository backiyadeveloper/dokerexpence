export function navigate(){
  const logout=()=>{
    localStorage.clear()
  }
  return{logout}
}
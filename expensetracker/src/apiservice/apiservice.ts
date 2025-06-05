import { url } from "./api";

export class ApiService {
  async apisendrequest(api: string, params: object, token: string | null, navigate: (path: string) => void): Promise<object> {
    try {
      const headers: HeadersInit = {
        "Content-Type": "application/json",
      };
      if (token) {
        headers["Authorization"] = `Bearer ${token}`;
      }
      const response = await fetch(`${url}${api}`, {
        method: "POST",
        headers: headers,
        body: JSON.stringify(params),
      });
      if (response.status === 200 || response.status===400 ) {
        console.log("400 error")
        const login = await response.json();
        return login;
      }
      else if(response.status === 204 ){
         
      }
      else if (response.status === 401) {
        navigate("/");
      }
      return {};
    } catch (error) {
      console.error("API request error:", error);
      throw error;
    }
  }
}

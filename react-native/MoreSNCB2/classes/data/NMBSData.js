import Station from "../global/Station";

class NMBSData {
    constructor() {
      // Initialize your class properties or state here
    }
  
    httpRequest(endpoint){
        const url = `https://api.irail.be${endpoint}`;
        return fetch(url)
        .then(response => response.json())
        .catch(error => {
            // Handle any errors
            throw new Error(error);
        });
    }

    async getStations(){
        const endpoint = "/stations/?format=json&lang=en";   
        return this.httpRequest(endpoint)
            .then((data) => {
                try{
                    //console.log(data);
                    const jsObject = data;
                
                    const jsArray = jsObject['station'];
                    //console.log(jsArray[0]);
                    const stationArray = [];

                    for (let i = 0; i < jsArray.length; i++){
                        stationArray.push(Station.fromJson(jsArray[i])); // adds station to array
                    }
            
                    return stationArray;
                } catch (error){
                    throw new Error(error);
                }
                }).catch((error)=>{
                    throw new Error("Error while accesing the API!");
                });        
                
    }
  }
  
  export default NMBSData;
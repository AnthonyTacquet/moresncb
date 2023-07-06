class Station {
    constructor(name) {
      // Initialize your class properties or state here
      this.name = name;
    }

    static fromJson(jsObject){
        return new Station(jsObject['name']);
    }
  }
  
  export default Station;
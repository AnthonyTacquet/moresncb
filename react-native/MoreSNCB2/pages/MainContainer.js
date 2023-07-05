import * as React from 'react';
import { StyleSheet, Text, View, Image, Dimensions, TextInput, TouchableOpacity } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';

// Screens
import HomePage from './HomePage';
import RoutePage from './RoutePage';

// Screen names
const homeName = "Home"
const routeName = "Route"

export default function MainContainer() {
    const [activeTab, setActiveTab] = React.useState(homeName);
  
    const renderScreen = () => {
      switch (activeTab) {
        case homeName:
          return <HomePage />;
        case routeName:
          return <RoutePage />;
        // Add more cases for additional screens
        default:
          return <HomePage/>;
      }
    };
  
    return (
      <View style={styles.container}>
        <View style={styles.contentContainer}>{renderScreen()}</View>
        <View style={styles.tabContainer}>
          <TouchableOpacity
            style={[styles.tabButton, activeTab === homeName && styles.activeTabButton]}
            onPress={() => setActiveTab(homeName)}
          >
            <Ionicons name="home-outline" size={24} color={activeTab === homeName ? '#0065ae' : '#808080'} />
            <Text style={styles.tabText}>Home</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={[styles.tabButton, activeTab === routeName && styles.activeTabButton]}
            onPress={() => setActiveTab(routeName)}
          >
            <Ionicons name="map-outline" size={24} color={activeTab === routeName ? '#0065ae' : '#808080'} />
            <Text style={styles.tabText}>Route</Text>
          </TouchableOpacity>
          {/* Add more tab buttons here */}
        </View>
      </View>
    );
  }
  
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      backgroundColor: '#202122',
    },
    tabContainer: {
      flexDirection: 'row',
      justifyContent: 'space-around',
      alignItems: 'center',
      height: 60,
      backgroundColor: '#202122',
      borderTopWidth: 1,
      borderTopColor: '#202122',
    },
    tabButton: {
      flex: 1,
      justifyContent: 'center',
      alignItems: 'center',
      height: '100%',
    },
    activeTabButton: {
      borderBottomWidth: 2,
      borderBottomColor: '#0065ae',
    },
    tabText: {
      fontSize: 12,
      marginTop: 2,
      color: '#808080',
    },
    contentContainer: {
      flex: 1,
    },
  });
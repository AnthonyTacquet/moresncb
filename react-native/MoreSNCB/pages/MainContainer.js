import * as React from 'react';
import { StyleSheet, Text, View, Image, Dimensions, TextInput } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import Ionicons from 'react-native-vector-icons/Ionicons';

// Screens
import HomePage from './HomePage';
import RoutePage from './RoutePage';

// Screen names
const homeName = "Home"
const routeName = "Route"

const Tab = createBottomTabNavigator();

export default function MainContainer() {
  return (
    <NavigationContainer>
        <Tab.Navigator
        initialRouteName={homeName}
        screenOptions={({route}) => ({
            tabBarIcon: ({focused, color, size}) => {
                let iconName;
                let rn = route.name;

                if (rn === homeName){
                    iconName = focused ? 'home': 'home-outline'
                } else if (rn === routeName){
                    iconName = focused ? 'home': 'home-outline'
                }

                return <Ionicons name={iconName} size={size} color={color} />
            },
        })}>

        <Tab.Screen name={homeName} component={HomePage} />
        <Tab.Screen name={routeName} component={RoutePage} />

        </Tab.Navigator>
    </NavigationContainer>
  );
};

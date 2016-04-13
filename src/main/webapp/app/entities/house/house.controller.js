(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HouseController', HouseController);

    HouseController.$inject = ['$scope', '$state', 'House'];

    function HouseController ($scope, $state, House) {
        var vm = this;
        vm.houses = [];
        vm.loadAll = function() {
            House.query(function(result) {
                vm.houses = result;
            });
        };

        vm.loadAll();
        
    }
})();

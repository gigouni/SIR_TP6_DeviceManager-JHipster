(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('HeaterController', HeaterController);

    HeaterController.$inject = ['$scope', '$state', 'Heater'];

    function HeaterController ($scope, $state, Heater) {
        var vm = this;
        vm.heaters = [];
        vm.loadAll = function() {
            Heater.query(function(result) {
                vm.heaters = result;
            });
        };

        vm.loadAll();
        
    }
})();

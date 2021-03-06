(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('PersonController', PersonController);

    PersonController.$inject = ['$scope', '$state', 'Person'];

    function PersonController ($scope, $state, Person) {
        var vm = this;
        vm.people = [];
        vm.loadAll = function() {
            Person.query(function(result) {
                vm.people = result;
            });
        };

        vm.loadAll();
        
    }
})();

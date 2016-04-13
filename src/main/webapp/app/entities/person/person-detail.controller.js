(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .controller('PersonDetailController', PersonDetailController);

    PersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Person', 'House'];

    function PersonDetailController($scope, $rootScope, $stateParams, entity, Person, House) {
        var vm = this;
        vm.person = entity;
        vm.load = function (id) {
            Person.get({id: id}, function(result) {
                vm.person = result;
            });
        };
        var unsubscribe = $rootScope.$on('sirTp6App:personUpdate', function(event, result) {
            vm.person = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

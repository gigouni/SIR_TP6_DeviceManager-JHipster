(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('heater', {
            parent: 'entity',
            url: '/heater',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Heaters'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/heater/heaters.html',
                    controller: 'HeaterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('heater-detail', {
            parent: 'entity',
            url: '/heater/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Heater'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/heater/heater-detail.html',
                    controller: 'HeaterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Heater', function($stateParams, Heater) {
                    return Heater.get({id : $stateParams.id});
                }]
            }
        })
        .state('heater.new', {
            parent: 'heater',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/heater/heater-dialog.html',
                    controller: 'HeaterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                place: null,
                                conso: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('heater', null, { reload: true });
                }, function() {
                    $state.go('heater');
                });
            }]
        })
        .state('heater.edit', {
            parent: 'heater',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/heater/heater-dialog.html',
                    controller: 'HeaterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Heater', function(Heater) {
                            return Heater.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('heater', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('heater.delete', {
            parent: 'heater',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/heater/heater-delete-dialog.html',
                    controller: 'HeaterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Heater', function(Heater) {
                            return Heater.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('heater', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

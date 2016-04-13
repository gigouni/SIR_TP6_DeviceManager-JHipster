(function() {
    'use strict';

    angular
        .module('sirTp6App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('electronic-device', {
            parent: 'entity',
            url: '/electronic-device',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ElectronicDevices'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/electronic-device/electronic-devices.html',
                    controller: 'ElectronicDeviceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('electronic-device-detail', {
            parent: 'entity',
            url: '/electronic-device/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ElectronicDevice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/electronic-device/electronic-device-detail.html',
                    controller: 'ElectronicDeviceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ElectronicDevice', function($stateParams, ElectronicDevice) {
                    return ElectronicDevice.get({id : $stateParams.id});
                }]
            }
        })
        .state('electronic-device.new', {
            parent: 'electronic-device',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electronic-device/electronic-device-dialog.html',
                    controller: 'ElectronicDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                conso: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('electronic-device', null, { reload: true });
                }, function() {
                    $state.go('electronic-device');
                });
            }]
        })
        .state('electronic-device.edit', {
            parent: 'electronic-device',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electronic-device/electronic-device-dialog.html',
                    controller: 'ElectronicDeviceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ElectronicDevice', function(ElectronicDevice) {
                            return ElectronicDevice.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('electronic-device', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('electronic-device.delete', {
            parent: 'electronic-device',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/electronic-device/electronic-device-delete-dialog.html',
                    controller: 'ElectronicDeviceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ElectronicDevice', function(ElectronicDevice) {
                            return ElectronicDevice.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('electronic-device', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-dataunacquiring', {
            parent: 'entity',
            url: '/freecom-dataunacquiring?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataunacquiring.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataunacquiring/freecom-dataunacquirings.html',
                    controller: 'Freecom_dataunacquiringController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_dataunacquiring');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-dataunacquiring-detail', {
            parent: 'entity',
            url: '/freecom-dataunacquiring/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataunacquiring.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataunacquiring/freecom-dataunacquiring-detail.html',
                    controller: 'Freecom_dataunacquiringDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_dataunacquiring');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_dataunacquiring', function($stateParams, Freecom_dataunacquiring) {
                    return Freecom_dataunacquiring.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-dataunacquiring.new', {
            parent: 'freecom-dataunacquiring',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataunacquiring/freecom-dataunacquiring-dialog.html',
                    controller: 'Freecom_dataunacquiringDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                last_name: null,
                                mother_last_name: null,
                                rfc: null,
                                curp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-dataunacquiring', null, { reload: true });
                }, function() {
                    $state.go('freecom-dataunacquiring');
                });
            }]
        })
        .state('freecom-dataunacquiring.edit', {
            parent: 'freecom-dataunacquiring',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataunacquiring/freecom-dataunacquiring-dialog.html',
                    controller: 'Freecom_dataunacquiringDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_dataunacquiring', function(Freecom_dataunacquiring) {
                            return Freecom_dataunacquiring.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataunacquiring', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-dataunacquiring.delete', {
            parent: 'freecom-dataunacquiring',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataunacquiring/freecom-dataunacquiring-delete-dialog.html',
                    controller: 'Freecom_dataunacquiringDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_dataunacquiring', function(Freecom_dataunacquiring) {
                            return Freecom_dataunacquiring.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataunacquiring', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-dataunenajenante', {
            parent: 'entity',
            url: '/freecom-dataunenajenante?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataunenajenante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataunenajenante/freecom-dataunenajenantes.html',
                    controller: 'Freecom_dataunenajenanteController',
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
                    $translatePartialLoader.addPart('freecom_dataunenajenante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-dataunenajenante-detail', {
            parent: 'entity',
            url: '/freecom-dataunenajenante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataunenajenante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataunenajenante/freecom-dataunenajenante-detail.html',
                    controller: 'Freecom_dataunenajenanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_dataunenajenante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_dataunenajenante', function($stateParams, Freecom_dataunenajenante) {
                    return Freecom_dataunenajenante.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-dataunenajenante.new', {
            parent: 'freecom-dataunenajenante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataunenajenante/freecom-dataunenajenante-dialog.html',
                    controller: 'Freecom_dataunenajenanteDialogController',
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
                    $state.go('freecom-dataunenajenante', null, { reload: true });
                }, function() {
                    $state.go('freecom-dataunenajenante');
                });
            }]
        })
        .state('freecom-dataunenajenante.edit', {
            parent: 'freecom-dataunenajenante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataunenajenante/freecom-dataunenajenante-dialog.html',
                    controller: 'Freecom_dataunenajenanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_dataunenajenante', function(Freecom_dataunenajenante) {
                            return Freecom_dataunenajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataunenajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-dataunenajenante.delete', {
            parent: 'freecom-dataunenajenante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataunenajenante/freecom-dataunenajenante-delete-dialog.html',
                    controller: 'Freecom_dataunenajenanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_dataunenajenante', function(Freecom_dataunenajenante) {
                            return Freecom_dataunenajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataunenajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

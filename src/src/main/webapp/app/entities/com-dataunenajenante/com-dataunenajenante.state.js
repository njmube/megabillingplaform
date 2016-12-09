(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-dataunenajenante', {
            parent: 'entity',
            url: '/com-dataunenajenante?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataunenajenante.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataunenajenante/com-dataunenajenantes.html',
                    controller: 'Com_dataunenajenanteController',
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
                    $translatePartialLoader.addPart('com_dataunenajenante');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-dataunenajenante-detail', {
            parent: 'entity',
            url: '/com-dataunenajenante/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataunenajenante.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataunenajenante/com-dataunenajenante-detail.html',
                    controller: 'Com_dataunenajenanteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_dataunenajenante');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_dataunenajenante', function($stateParams, Com_dataunenajenante) {
                    return Com_dataunenajenante.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-dataunenajenante.new', {
            parent: 'com-dataunenajenante',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataunenajenante/com-dataunenajenante-dialog.html',
                    controller: 'Com_dataunenajenanteDialogController',
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
                    $state.go('com-dataunenajenante', null, { reload: true });
                }, function() {
                    $state.go('com-dataunenajenante');
                });
            }]
        })
        .state('com-dataunenajenante.edit', {
            parent: 'com-dataunenajenante',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataunenajenante/com-dataunenajenante-dialog.html',
                    controller: 'Com_dataunenajenanteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_dataunenajenante', function(Com_dataunenajenante) {
                            return Com_dataunenajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataunenajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-dataunenajenante.delete', {
            parent: 'com-dataunenajenante',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataunenajenante/com-dataunenajenante-delete-dialog.html',
                    controller: 'Com_dataunenajenanteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_dataunenajenante', function(Com_dataunenajenante) {
                            return Com_dataunenajenante.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataunenajenante', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

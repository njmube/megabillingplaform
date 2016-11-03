(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-ecc-11', {
            parent: 'entity',
            url: '/com-ecc-11?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ecc_11.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ecc-11/com-ecc-11-s.html',
                    controller: 'Com_ecc_11Controller',
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
                    $translatePartialLoader.addPart('com_ecc_11');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-ecc-11-detail', {
            parent: 'entity',
            url: '/com-ecc-11/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ecc_11.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ecc-11/com-ecc-11-detail.html',
                    controller: 'Com_ecc_11DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_ecc_11');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_ecc_11', function($stateParams, Com_ecc_11) {
                    return Com_ecc_11.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-ecc-11.new', {
            parent: 'com-ecc-11',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11/com-ecc-11-dialog.html',
                    controller: 'Com_ecc_11DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                type_operation: null,
                                number_account: null,
                                subtotal: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11', null, { reload: true });
                }, function() {
                    $state.go('com-ecc-11');
                });
            }]
        })
        .state('com-ecc-11.edit', {
            parent: 'com-ecc-11',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11/com-ecc-11-dialog.html',
                    controller: 'Com_ecc_11DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_ecc_11', function(Com_ecc_11) {
                            return Com_ecc_11.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-ecc-11.delete', {
            parent: 'com-ecc-11',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11/com-ecc-11-delete-dialog.html',
                    controller: 'Com_ecc_11DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_ecc_11', function(Com_ecc_11) {
                            return Com_ecc_11.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

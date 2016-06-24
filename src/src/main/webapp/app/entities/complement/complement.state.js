(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('complement', {
            parent: 'entity',
            url: '/complement?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.complement.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/complement/complements.html',
                    controller: 'ComplementController',
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
                    $translatePartialLoader.addPart('complement');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('complement-detail', {
            parent: 'entity',
            url: '/complement/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.complement.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/complement/complement-detail.html',
                    controller: 'ComplementDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('complement');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Complement', function($stateParams, Complement) {
                    return Complement.get({id : $stateParams.id});
                }]
            }
        })
        .state('complement.new', {
            parent: 'complement',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/complement/complement-dialog.html',
                    controller: 'ComplementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('complement', null, { reload: true });
                }, function() {
                    $state.go('complement');
                });
            }]
        })
        .state('complement.edit', {
            parent: 'complement',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/complement/complement-dialog.html',
                    controller: 'ComplementDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Complement', function(Complement) {
                            return Complement.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('complement', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('complement.delete', {
            parent: 'complement',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/complement/complement-delete-dialog.html',
                    controller: 'ComplementDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Complement', function(Complement) {
                            return Complement.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('complement', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

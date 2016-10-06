(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ring-pack', {
            parent: 'entity',
            url: '/ring-pack?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.ring_pack.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ring-pack/ring-packs.html',
                    controller: 'Ring_packController',
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
                    $translatePartialLoader.addPart('ring_pack');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ring-pack-detail', {
            parent: 'entity',
            url: '/ring-pack/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.ring_pack.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ring-pack/ring-pack-detail.html',
                    controller: 'Ring_packDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ring_pack');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Ring_pack', function($stateParams, Ring_pack) {
                    return Ring_pack.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('ring-pack.new', {
            parent: 'ring-pack',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ring-pack/ring-pack-dialog.html',
                    controller: 'Ring_packDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                currency: null,
                                description: null,
                                price: null,
                                reference_code: null,
                                rings: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ring-pack', null, { reload: true });
                }, function() {
                    $state.go('ring-pack');
                });
            }]
        })
        .state('ring-pack.edit', {
            parent: 'ring-pack',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ring-pack/ring-pack-dialog.html',
                    controller: 'Ring_packDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Ring_pack', function(Ring_pack) {
                            return Ring_pack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ring-pack', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ring-pack.delete', {
            parent: 'ring-pack',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ring-pack/ring-pack-delete-dialog.html',
                    controller: 'Ring_packDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Ring_pack', function(Ring_pack) {
                            return Ring_pack.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ring-pack', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

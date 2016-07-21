(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-features-work-piece', {
            parent: 'entity',
            url: '/c-features-work-piece?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_features_work_piece.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-features-work-piece/c-features-work-pieces.html',
                    controller: 'C_features_work_pieceController',
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
                    $translatePartialLoader.addPart('c_features_work_piece');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-features-work-piece-detail', {
            parent: 'entity',
            url: '/c-features-work-piece/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_features_work_piece.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-features-work-piece/c-features-work-piece-detail.html',
                    controller: 'C_features_work_pieceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_features_work_piece');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_features_work_piece', function($stateParams, C_features_work_piece) {
                    return C_features_work_piece.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-features-work-piece.new', {
            parent: 'c-features-work-piece',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-features-work-piece/c-features-work-piece-dialog.html',
                    controller: 'C_features_work_pieceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-features-work-piece', null, { reload: true });
                }, function() {
                    $state.go('c-features-work-piece');
                });
            }]
        })
        .state('c-features-work-piece.edit', {
            parent: 'c-features-work-piece',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-features-work-piece/c-features-work-piece-dialog.html',
                    controller: 'C_features_work_pieceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_features_work_piece', function(C_features_work_piece) {
                            return C_features_work_piece.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-features-work-piece', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-features-work-piece.delete', {
            parent: 'c-features-work-piece',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-features-work-piece/c-features-work-piece-delete-dialog.html',
                    controller: 'C_features_work_pieceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_features_work_piece', function(C_features_work_piece) {
                            return C_features_work_piece.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-features-work-piece', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

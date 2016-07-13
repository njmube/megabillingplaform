(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('features-work-piece', {
            parent: 'entity',
            url: '/features-work-piece?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.features_work_piece.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/features-work-piece/features-work-pieces.html',
                    controller: 'Features_work_pieceController',
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
                    $translatePartialLoader.addPart('features_work_piece');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('features-work-piece-detail', {
            parent: 'entity',
            url: '/features-work-piece/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.features_work_piece.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/features-work-piece/features-work-piece-detail.html',
                    controller: 'Features_work_pieceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('features_work_piece');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Features_work_piece', function($stateParams, Features_work_piece) {
                    return Features_work_piece.get({id : $stateParams.id});
                }]
            }
        })
        .state('features-work-piece.new', {
            parent: 'features-work-piece',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/features-work-piece/features-work-piece-dialog.html',
                    controller: 'Features_work_pieceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('features-work-piece', null, { reload: true });
                }, function() {
                    $state.go('features-work-piece');
                });
            }]
        })
        .state('features-work-piece.edit', {
            parent: 'features-work-piece',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/features-work-piece/features-work-piece-dialog.html',
                    controller: 'Features_work_pieceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Features_work_piece', function(Features_work_piece) {
                            return Features_work_piece.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('features-work-piece', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('features-work-piece.delete', {
            parent: 'features-work-piece',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/features-work-piece/features-work-piece-delete-dialog.html',
                    controller: 'Features_work_pieceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Features_work_piece', function(Features_work_piece) {
                            return Features_work_piece.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('features-work-piece', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

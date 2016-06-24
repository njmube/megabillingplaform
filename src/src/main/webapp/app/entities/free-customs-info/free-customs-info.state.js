(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-customs-info', {
            parent: 'entity',
            url: '/free-customs-info?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_customs_info.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-customs-info/free-customs-infos.html',
                    controller: 'Free_customs_infoController',
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
                    $translatePartialLoader.addPart('free_customs_info');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-customs-info-detail', {
            parent: 'entity',
            url: '/free-customs-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_customs_info.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-customs-info/free-customs-info-detail.html',
                    controller: 'Free_customs_infoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_customs_info');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_customs_info', function($stateParams, Free_customs_info) {
                    return Free_customs_info.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-customs-info.new', {
            parent: 'free-customs-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-customs-info/free-customs-info-dialog.html',
                    controller: 'Free_customs_infoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number_doc: null,
                                date: null,
                                customs: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-customs-info', null, { reload: true });
                }, function() {
                    $state.go('free-customs-info');
                });
            }]
        })
        .state('free-customs-info.edit', {
            parent: 'free-customs-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-customs-info/free-customs-info-dialog.html',
                    controller: 'Free_customs_infoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_customs_info', function(Free_customs_info) {
                            return Free_customs_info.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-customs-info', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-customs-info.delete', {
            parent: 'free-customs-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-customs-info/free-customs-info-delete-dialog.html',
                    controller: 'Free_customs_infoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_customs_info', function(Free_customs_info) {
                            return Free_customs_info.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-customs-info', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

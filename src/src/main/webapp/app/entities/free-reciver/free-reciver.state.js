(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-reciver', {
            parent: 'entity',
            url: '/free-reciver?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_reciver.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-reciver/free-recivers.html',
                    controller: 'Free_reciverController',
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
                    $translatePartialLoader.addPart('free_reciver');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-reciver-detail', {
            parent: 'entity',
            url: '/free-reciver/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_reciver.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-reciver/free-reciver-detail.html',
                    controller: 'Free_reciverDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_reciver');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_reciver', function($stateParams, Free_reciver) {
                    return Free_reciver.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-reciver.new', {
            parent: 'free-reciver',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-reciver/free-reciver-dialog.html',
                    controller: 'Free_reciverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rfc: null,
                                business_name: null,
                                email: null,
                                street: null,
                                no_int: null,
                                no_ext: null,
                                reference: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-reciver', null, { reload: true });
                }, function() {
                    $state.go('free-reciver');
                });
            }]
        })
        .state('free-reciver.edit', {
            parent: 'free-reciver',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-reciver/free-reciver-dialog.html',
                    controller: 'Free_reciverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_reciver', function(Free_reciver) {
                            return Free_reciver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-reciver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-reciver.delete', {
            parent: 'free-reciver',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-reciver/free-reciver-delete-dialog.html',
                    controller: 'Free_reciverDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_reciver', function(Free_reciver) {
                            return Free_reciver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-reciver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

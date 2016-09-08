(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-receiver', {
            parent: 'entity',
            url: '/free-receiver?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_receiver.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-receiver/free-receivers.html',
                    controller: 'Free_receiverController',
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
                    $translatePartialLoader.addPart('free_receiver');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-receiver-detail', {
            parent: 'entity',
            url: '/free-receiver/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_receiver.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-receiver/free-receiver-detail.html',
                    controller: 'Free_receiverDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_receiver');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_receiver', function($stateParams, Free_receiver) {
                    return Free_receiver.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-receiver.new', {
            parent: 'free-receiver',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-receiver/free-receiver-dialog.html',
                    controller: 'Free_receiverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rfc: null,
                                business_name: null,
                                email: null,
                                activated: false,
                                create_date: null,
                                street: null,
                                no_ext: null,
                                no_int: null,
                                reference: null,
                                phone: null,
                                location: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-receiver', null, { reload: true });
                }, function() {
                    $state.go('free-receiver');
                });
            }]
        })
        .state('free-receiver.edit', {
            parent: 'free-receiver',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-receiver/free-receiver-dialog.html',
                    controller: 'Free_receiverDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_receiver', function(Free_receiver) {
                            return Free_receiver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-receiver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-receiver.delete', {
            parent: 'free-receiver',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-receiver/free-receiver-delete-dialog.html',
                    controller: 'Free_receiverDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_receiver', function(Free_receiver) {
                            return Free_receiver.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-receiver', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

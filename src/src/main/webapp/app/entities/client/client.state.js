(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('client', {
            parent: 'entity',
            url: '/client?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.client.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client/clients.html',
                    controller: 'ClientController',
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
                    $translatePartialLoader.addPart('client');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('client-detail', {
            parent: 'entity',
            url: '/client/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.client.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/client/client-detail.html',
                    controller: 'ClientDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('client');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Client', function($stateParams, Client) {
                    return Client.get({id : $stateParams.id});
                }]
            }
        })
        .state('client.new', {
            parent: 'client',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client/client-dialog.html',
                    controller: 'ClientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rfc: null,
                                bussinesname: null,
                                email: null,
                                email2: null,
                                phone: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('client', null, { reload: true });
                }, function() {
                    $state.go('client');
                });
            }]
        })
        .state('client.edit', {
            parent: 'client',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client/client-dialog.html',
                    controller: 'ClientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Client', function(Client) {
                            return Client.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('client', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('client.delete', {
            parent: 'client',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/client/client-delete-dialog.html',
                    controller: 'ClientDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Client', function(Client) {
                            return Client.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('client', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

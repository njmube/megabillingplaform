(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-addressee', {
            parent: 'entity',
            url: '/com-addressee?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_addressee.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-addressee/com-addressees.html',
                    controller: 'Com_addresseeController',
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
                    $translatePartialLoader.addPart('com_addressee');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-addressee-detail', {
            parent: 'entity',
            url: '/com-addressee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_addressee.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-addressee/com-addressee-detail.html',
                    controller: 'Com_addresseeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_addressee');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_addressee', function($stateParams, Com_addressee) {
                    return Com_addressee.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-addressee.new', {
            parent: 'com-addressee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-addressee/com-addressee-dialog.html',
                    controller: 'Com_addresseeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                street: null,
                                no_ext: null,
                                no_int: null,
                                locate: null,
                                reference: null,
                                numregidtrib: null,
                                rfc: null,
                                curp: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-addressee', null, { reload: true });
                }, function() {
                    $state.go('com-addressee');
                });
            }]
        })
        .state('com-addressee.edit', {
            parent: 'com-addressee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-addressee/com-addressee-dialog.html',
                    controller: 'Com_addresseeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_addressee', function(Com_addressee) {
                            return Com_addressee.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-addressee', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-addressee.delete', {
            parent: 'com-addressee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-addressee/com-addressee-delete-dialog.html',
                    controller: 'Com_addresseeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_addressee', function(Com_addressee) {
                            return Com_addressee.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-addressee', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

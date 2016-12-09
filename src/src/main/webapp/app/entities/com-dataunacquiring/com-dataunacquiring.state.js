(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-dataunacquiring', {
            parent: 'entity',
            url: '/com-dataunacquiring?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataunacquiring.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataunacquiring/com-dataunacquirings.html',
                    controller: 'Com_dataunacquiringController',
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
                    $translatePartialLoader.addPart('com_dataunacquiring');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-dataunacquiring-detail', {
            parent: 'entity',
            url: '/com-dataunacquiring/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataunacquiring.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataunacquiring/com-dataunacquiring-detail.html',
                    controller: 'Com_dataunacquiringDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_dataunacquiring');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_dataunacquiring', function($stateParams, Com_dataunacquiring) {
                    return Com_dataunacquiring.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-dataunacquiring.new', {
            parent: 'com-dataunacquiring',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataunacquiring/com-dataunacquiring-dialog.html',
                    controller: 'Com_dataunacquiringDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                last_name: null,
                                mother_last_name: null,
                                rfc: null,
                                curp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-dataunacquiring', null, { reload: true });
                }, function() {
                    $state.go('com-dataunacquiring');
                });
            }]
        })
        .state('com-dataunacquiring.edit', {
            parent: 'com-dataunacquiring',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataunacquiring/com-dataunacquiring-dialog.html',
                    controller: 'Com_dataunacquiringDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_dataunacquiring', function(Com_dataunacquiring) {
                            return Com_dataunacquiring.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataunacquiring', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-dataunacquiring.delete', {
            parent: 'com-dataunacquiring',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataunacquiring/com-dataunacquiring-delete-dialog.html',
                    controller: 'Com_dataunacquiringDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_dataunacquiring', function(Com_dataunacquiring) {
                            return Com_dataunacquiring.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataunacquiring', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

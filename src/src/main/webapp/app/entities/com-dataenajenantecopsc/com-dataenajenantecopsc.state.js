(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-dataenajenantecopsc', {
            parent: 'entity',
            url: '/com-dataenajenantecopsc?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataenajenantecopsc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataenajenantecopsc/com-dataenajenantecopscs.html',
                    controller: 'Com_dataenajenantecopscController',
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
                    $translatePartialLoader.addPart('com_dataenajenantecopsc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-dataenajenantecopsc-detail', {
            parent: 'entity',
            url: '/com-dataenajenantecopsc/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataenajenantecopsc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataenajenantecopsc/com-dataenajenantecopsc-detail.html',
                    controller: 'Com_dataenajenantecopscDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_dataenajenantecopsc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_dataenajenantecopsc', function($stateParams, Com_dataenajenantecopsc) {
                    return Com_dataenajenantecopsc.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-dataenajenantecopsc.new', {
            parent: 'com-dataenajenantecopsc',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataenajenantecopsc/com-dataenajenantecopsc-dialog.html',
                    controller: 'Com_dataenajenantecopscDialogController',
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
                                percentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-dataenajenantecopsc', null, { reload: true });
                }, function() {
                    $state.go('com-dataenajenantecopsc');
                });
            }]
        })
        .state('com-dataenajenantecopsc.edit', {
            parent: 'com-dataenajenantecopsc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataenajenantecopsc/com-dataenajenantecopsc-dialog.html',
                    controller: 'Com_dataenajenantecopscDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_dataenajenantecopsc', function(Com_dataenajenantecopsc) {
                            return Com_dataenajenantecopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataenajenantecopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-dataenajenantecopsc.delete', {
            parent: 'com-dataenajenantecopsc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataenajenantecopsc/com-dataenajenantecopsc-delete-dialog.html',
                    controller: 'Com_dataenajenantecopscDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_dataenajenantecopsc', function(Com_dataenajenantecopsc) {
                            return Com_dataenajenantecopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataenajenantecopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

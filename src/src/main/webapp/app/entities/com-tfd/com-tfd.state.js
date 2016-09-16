(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-tfd', {
            parent: 'entity',
            url: '/com-tfd?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_tfd.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-tfd/com-tfds.html',
                    controller: 'Com_tfdController',
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
                    $translatePartialLoader.addPart('com_tfd');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-tfd-detail', {
            parent: 'entity',
            url: '/com-tfd/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_tfd.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-tfd/com-tfd-detail.html',
                    controller: 'Com_tfdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_tfd');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_tfd', function($stateParams, Com_tfd) {
                    return Com_tfd.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('com-tfd.new', {
            parent: 'com-tfd',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-tfd/com-tfd-dialog.html',
                    controller: 'Com_tfdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                uuid: null,
                                stamp_date: null,
                                stamp_cfd: null,
                                sat_number_certificate: null,
                                stamp_sat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-tfd', null, { reload: true });
                }, function() {
                    $state.go('com-tfd');
                });
            }]
        })
        .state('com-tfd.edit', {
            parent: 'com-tfd',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-tfd/com-tfd-dialog.html',
                    controller: 'Com_tfdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_tfd', function(Com_tfd) {
                            return Com_tfd.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-tfd', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-tfd.delete', {
            parent: 'com-tfd',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-tfd/com-tfd-delete-dialog.html',
                    controller: 'Com_tfdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_tfd', function(Com_tfd) {
                            return Com_tfd.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-tfd', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

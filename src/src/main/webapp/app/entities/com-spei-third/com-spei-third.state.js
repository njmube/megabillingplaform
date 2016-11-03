(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-spei-third', {
            parent: 'entity',
            url: '/com-spei-third?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_spei_third.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-spei-third/com-spei-thirds.html',
                    controller: 'Com_spei_thirdController',
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
                    $translatePartialLoader.addPart('com_spei_third');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-spei-third-detail', {
            parent: 'entity',
            url: '/com-spei-third/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_spei_third.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-spei-third/com-spei-third-detail.html',
                    controller: 'Com_spei_thirdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_spei_third');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_spei_third', function($stateParams, Com_spei_third) {
                    return Com_spei_third.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-spei-third.new', {
            parent: 'com-spei-third',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-spei-third/com-spei-third-dialog.html',
                    controller: 'Com_spei_thirdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date_operation: null,
                                hour: null,
                                key_spei: null,
                                stamp: null,
                                number_certificate: null,
                                cda: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-spei-third', null, { reload: true });
                }, function() {
                    $state.go('com-spei-third');
                });
            }]
        })
        .state('com-spei-third.edit', {
            parent: 'com-spei-third',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-spei-third/com-spei-third-dialog.html',
                    controller: 'Com_spei_thirdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_spei_third', function(Com_spei_third) {
                            return Com_spei_third.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-spei-third', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-spei-third.delete', {
            parent: 'com-spei-third',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-spei-third/com-spei-third-delete-dialog.html',
                    controller: 'Com_spei_thirdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_spei_third', function(Com_spei_third) {
                            return Com_spei_third.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-spei-third', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

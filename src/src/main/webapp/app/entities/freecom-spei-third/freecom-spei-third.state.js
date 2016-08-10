(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-spei-third', {
            parent: 'entity',
            url: '/freecom-spei-third?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_spei_third.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-spei-third/freecom-spei-thirds.html',
                    controller: 'Freecom_spei_thirdController',
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
                    $translatePartialLoader.addPart('freecom_spei_third');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-spei-third-detail', {
            parent: 'entity',
            url: '/freecom-spei-third/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_spei_third.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-spei-third/freecom-spei-third-detail.html',
                    controller: 'Freecom_spei_thirdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_spei_third');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_spei_third', function($stateParams, Freecom_spei_third) {
                    return Freecom_spei_third.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-spei-third.new', {
            parent: 'freecom-spei-third',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-spei-third/freecom-spei-third-dialog.html',
                    controller: 'Freecom_spei_thirdDialogController',
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
                    $state.go('freecom-spei-third', null, { reload: true });
                }, function() {
                    $state.go('freecom-spei-third');
                });
            }]
        })
        .state('freecom-spei-third.edit', {
            parent: 'freecom-spei-third',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-spei-third/freecom-spei-third-dialog.html',
                    controller: 'Freecom_spei_thirdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_spei_third', function(Freecom_spei_third) {
                            return Freecom_spei_third.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-spei-third', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-spei-third.delete', {
            parent: 'freecom-spei-third',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-spei-third/freecom-spei-third-delete-dialog.html',
                    controller: 'Freecom_spei_thirdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_spei_third', function(Freecom_spei_third) {
                            return Freecom_spei_third.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-spei-third', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

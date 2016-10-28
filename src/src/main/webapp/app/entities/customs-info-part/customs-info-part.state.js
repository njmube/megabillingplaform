(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customs-info-part', {
            parent: 'entity',
            url: '/customs-info-part?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.customs_info_part.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customs-info-part/customs-info-parts.html',
                    controller: 'Customs_info_partController',
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
                    $translatePartialLoader.addPart('customs_info_part');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customs-info-part-detail', {
            parent: 'entity',
            url: '/customs-info-part/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.customs_info_part.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customs-info-part/customs-info-part-detail.html',
                    controller: 'Customs_info_partDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customs_info_part');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Customs_info_part', function($stateParams, Customs_info_part) {
                    return Customs_info_part.get({id : $stateParams.id});
                }]
            }
        })
        .state('customs-info-part.new', {
            parent: 'customs-info-part',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customs-info-part/customs-info-part-dialog.html',
                    controller: 'Customs_info_partDialogController',
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
                    $state.go('customs-info-part', null, { reload: true });
                }, function() {
                    $state.go('customs-info-part');
                });
            }]
        })
        .state('customs-info-part.edit', {
            parent: 'customs-info-part',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customs-info-part/customs-info-part-dialog.html',
                    controller: 'Customs_info_partDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Customs_info_part', function(Customs_info_part) {
                            return Customs_info_part.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('customs-info-part', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customs-info-part.delete', {
            parent: 'customs-info-part',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customs-info-part/customs-info-part-delete-dialog.html',
                    controller: 'Customs_info_partDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Customs_info_part', function(Customs_info_part) {
                            return Customs_info_part.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('customs-info-part', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

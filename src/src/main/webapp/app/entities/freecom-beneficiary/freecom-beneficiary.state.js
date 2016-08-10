(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-beneficiary', {
            parent: 'entity',
            url: '/freecom-beneficiary?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_beneficiary.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-beneficiary/freecom-beneficiaries.html',
                    controller: 'Freecom_beneficiaryController',
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
                    $translatePartialLoader.addPart('freecom_beneficiary');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-beneficiary-detail', {
            parent: 'entity',
            url: '/freecom-beneficiary/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_beneficiary.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-beneficiary/freecom-beneficiary-detail.html',
                    controller: 'Freecom_beneficiaryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_beneficiary');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_beneficiary', function($stateParams, Freecom_beneficiary) {
                    return Freecom_beneficiary.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-beneficiary.new', {
            parent: 'freecom-beneficiary',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-beneficiary/freecom-beneficiary-dialog.html',
                    controller: 'Freecom_beneficiaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                receiver_bank: null,
                                name: null,
                                type_account: null,
                                account: null,
                                rfc: null,
                                concept: null,
                                iva: null,
                                payment_amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-beneficiary', null, { reload: true });
                }, function() {
                    $state.go('freecom-beneficiary');
                });
            }]
        })
        .state('freecom-beneficiary.edit', {
            parent: 'freecom-beneficiary',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-beneficiary/freecom-beneficiary-dialog.html',
                    controller: 'Freecom_beneficiaryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_beneficiary', function(Freecom_beneficiary) {
                            return Freecom_beneficiary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-beneficiary', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-beneficiary.delete', {
            parent: 'freecom-beneficiary',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-beneficiary/freecom-beneficiary-delete-dialog.html',
                    controller: 'Freecom_beneficiaryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_beneficiary', function(Freecom_beneficiary) {
                            return Freecom_beneficiary.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-beneficiary', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
